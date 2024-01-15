package io.wowcollector.restserviceimpl.services.battlenet;

import com.google.gson.Gson;
import io.wowcollector.common.data.BlizzardAssetKey;
import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacter;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.repository.achievement.AchievementCategoryDocument;
import io.wowcollector.entityview.response.character.CharacterMediaResponse;
import io.wowcollector.entityview.response.character.CharacterResponse;
import io.wowcollector.entityview.response.collection.RegionRealmResponse;
import io.wowcollector.entityview.response.collection.achievement.AchievementCategoryResponse;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.achievement.AchievementCategoryRepository;
import io.wowcollector.repository.repositories.realm.RealmRepository;
import io.wowcollector.restservice.battlenet.RestBattleNetService;
import io.wowcollector.service.http.BattleNetHttpService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestBattleNetServiceImpl implements RestBattleNetService {
    @Inject
    private RepositoryFactory myRepositoryFactory;
    @Inject
    private BattleNetHttpService myBattleNetHttpService;

    @Override
    public Response getRealmsAndRegions() {
        try {
            RealmRepository realmRepository = myRepositoryFactory.of(RealmRepository.class);
            RegionRealmResponse regionRealmResponse = RegionRealmResponse.newBuilder()
                    .withRealms(realmRepository.getAll()
                                        .stream()
                                        .map(item -> item.newBuilderFromCurrent()
                                                .withObjectId(null)
                                                .build())
                                        .collect(Collectors.toList()))
                    .withRegions(Arrays.asList(BlizzardRegion.values()))
                    .build();

            return Response.status(Response.Status.OK)
                    .entity(new Gson().toJson(regionRealmResponse))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public Response getAchievementRootCategories() {
        try {
            AchievementCategoryRepository achievementCategoryRepository =
                    myRepositoryFactory.of(AchievementCategoryRepository.class);
            List<AchievementCategoryResponse> result = achievementCategoryRepository.getAll()
                    .stream()
                    .filter(AchievementCategoryDocument::isRootCategory)
                    .map(item -> AchievementCategoryResponse.newBuilder()
                            .withName(item.getName())
                            .withDisplayOrder(item.getDisplayOrder())
                            .build())
                    .toList();

            return Response.status(Response.Status.OK)
                    .entity(new Gson().toJson(result))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    public Response getCharacter(String region, String realm, String character) {
        try {
            BattleNetCharacter battleNetCharacter = myBattleNetHttpService.getCharacter(
                    BlizzardRegion.get(region),
                    realm,
                    character);
            if (battleNetCharacter == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Not found")
                        .build();
            }

            return Response
                    .status(Response.Status.OK)
                    .entity(new Gson().toJson(getCharacter(battleNetCharacter, BlizzardRegion.get(region))))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    private CharacterResponse getCharacter(BattleNetCharacter battleNetCharacter, BlizzardRegion region) {
        CharacterResponse.Builder builder = CharacterResponse.newBuilder()
                .withId(battleNetCharacter.getId())
                .withName(battleNetCharacter.getName())
                .withRealm(battleNetCharacter.getRealm()
                                   .getSlug())
                .withRegion(region)
                .withLevel(battleNetCharacter.getLevel())
                .withFaction(battleNetCharacter.getFaction()
                                     .getType())
                .withRace(battleNetCharacter.getRace()
                                  .getName())
                .withClazz(battleNetCharacter.getClazz()
                                   .getName())
                .withGender(battleNetCharacter.getGender()
                                    .getType())
                .withSpecialization(battleNetCharacter.getActiveSpec()
                                            .getName());

        myBattleNetHttpService.getCharacterMedia(region,
                                                 battleNetCharacter.getRealm()
                                                         .getSlug(),
                                                 battleNetCharacter.getName())
                .ifPresent(media -> builder.withMedia(getCharacterMedia(media)));

        return builder.build();
    }

    private CharacterMediaResponse getCharacterMedia(BattleNetMedia media) {
        CharacterMediaResponse.Builder builder = CharacterMediaResponse.newBuilder();
        if (media == null) {
            return builder.build();
        }
        return builder.withAvatar(media.getAssetWithKey(BlizzardAssetKey.AVATAR))
                .withInset(media.getAssetWithKey(BlizzardAssetKey.INSET))
                .withMainRaw(media.getAssetWithKey(BlizzardAssetKey.MAIN_RAW))
                .build();
    }
}
