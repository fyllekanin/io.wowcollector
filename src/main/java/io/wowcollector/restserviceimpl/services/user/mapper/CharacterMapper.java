package io.wowcollector.restserviceimpl.services.user.mapper;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacter;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.http.battlenet.BattleNetWowAccountCharacter;
import io.wowcollector.entityview.repository.user.UserCharacter;
import io.wowcollector.service.http.BattleNetHttpService;

public class CharacterMapper implements Runnable {
    private final BattleNetHttpService myBattleNetHttpService;
    private final BlizzardRegion myRegion;
    private final BattleNetWowAccountCharacter myCharacter;
    private UserCharacter myResult;

    public CharacterMapper(BattleNetHttpService battleNetHttpService, BlizzardRegion region,
                           BattleNetWowAccountCharacter character) {
        myBattleNetHttpService = battleNetHttpService;
        myRegion = region;
        myCharacter = character;
    }

    @Override
    public void run() {
        BattleNetCharacter character = myBattleNetHttpService.getCharacter(myRegion, myCharacter.getRealm()
                .getSlug(), myCharacter.getName());
        if (character == null) {
            return;
        }

        BattleNetMedia media = myBattleNetHttpService.getCharacterMedia(myRegion, myCharacter.getRealm()
                                                                                .getSlug(),
                                                                        myCharacter.getName())
                .orElse(null);
        if (media == null) {
            return;
        }

        myResult = UserCharacter.newBuilder()
                .withFaction(myCharacter.getFaction())
                .withRealm(myCharacter.getRealm())
                .withLevel(myCharacter.getLevel())
                .withName(myCharacter.getName())
                .withId(myCharacter.getId())
                .withRegion(myRegion)
                .withMedia(media)
                .withRace(myCharacter.getPlayableRace())
                .withGender(myCharacter.getGender())
                .withClazz(myCharacter.getPlayableClass())
                .withSpecialization(character.getActiveSpec())
                .build();

    }

    public UserCharacter getResult() {
        return myResult;
    }
}