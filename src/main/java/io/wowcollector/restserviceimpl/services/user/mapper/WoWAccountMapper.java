package io.wowcollector.restserviceimpl.services.user.mapper;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.common.util.ListUtils;
import io.wowcollector.entityview.http.battlenet.BattleNetWowAccount;
import io.wowcollector.entityview.http.battlenet.BattleNetWowAccountCharacter;
import io.wowcollector.entityview.repository.user.UserCharacter;
import io.wowcollector.service.http.BattleNetHttpService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WoWAccountMapper {
    private final BattleNetHttpService myBattleNetHttpService;
    private final BlizzardRegion myRegion;
    private final int myLowestCharacterLevel;

    public WoWAccountMapper(BattleNetHttpService battleNetHttpService, BlizzardRegion region,
                            int lowestCharacterLevel) {
        myBattleNetHttpService = battleNetHttpService;
        myRegion = region;
        myLowestCharacterLevel = lowestCharacterLevel;
    }

    public List<UserCharacter> getCharacters(BattleNetWowAccount wowAccount) {
        List<List<BattleNetWowAccountCharacter>> chunks = ListUtils.getChunks(50, wowAccount.getCharacters());
        List<UserCharacter> result = new ArrayList<>();

        for (List<BattleNetWowAccountCharacter> chunk : chunks) {
            List<CharacterMapper> mappers = chunk.stream()
                    .filter(item -> item.getLevel() >= myLowestCharacterLevel)
                    .map(item -> new CharacterMapper(myBattleNetHttpService, myRegion, item))
                    .toList();
            List<Thread> threads = mappers.stream()
                    .map(Thread::new)
                    .peek(Thread::start)
                    .toList();
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    // Empty
                }
            }
            result.addAll(mappers.stream()
                                  .map(CharacterMapper::getResult)
                                  .filter(Objects::nonNull)
                                  .toList());
        }

        return result;
    }
}
