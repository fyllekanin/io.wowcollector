package io.wowcollector.restserviceimpl.services.user.mapper;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetOauth;
import io.wowcollector.entityview.http.battlenet.BattleNetProfileWow;
import io.wowcollector.entityview.repository.user.UserCharacter;
import io.wowcollector.service.http.BattleNetHttpService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class WoWProfileMapper {
    private final BattleNetHttpService myBattleNetHttpService;
    private final int myLowestCharacterLevel;

    public WoWProfileMapper(BattleNetHttpService battleNetHttpService, int lowestCharacterLevel) {
        myBattleNetHttpService = battleNetHttpService;
        myLowestCharacterLevel = lowestCharacterLevel;
    }

    public List<UserCharacter> getCharacters(BlizzardRegion region, BattleNetOauth oauth) {
        WoWAccountMapper accountMapper = new WoWAccountMapper(myBattleNetHttpService, region, myLowestCharacterLevel);
        BattleNetProfileWow profile = myBattleNetHttpService.getWoWProfile(region, oauth.getAccessToken());
        if (profile == null || profile.getWoWAccounts()
                .isEmpty()) {
            return Collections.emptyList();
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        try {
            return CompletableFuture.supplyAsync(() ->
                                                         profile.getWoWAccounts()
                                                                 .stream()
                                                                 .flatMap(item -> accountMapper.getCharacters(item)
                                                                         .stream())
                                                                 .collect(Collectors.toList())
                            , forkJoinPool)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
