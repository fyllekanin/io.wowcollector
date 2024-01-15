package io.wowcollector.restserviceimpl.services.collections;

import com.google.gson.Gson;
import io.quarkus.cache.CacheResult;
import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacter;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievements;
import io.wowcollector.entityview.http.battlenet.BattleNetUserMountCollection;
import io.wowcollector.entityview.repository.collectionview.MountCollectionViewDocument;
import io.wowcollector.entityview.response.collection.CharacterAchievementCollectionResponse;
import io.wowcollector.entityview.response.collection.CharacterMountCollectionResponse;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.mountcollectionview.MountCollectionViewRepository;
import io.wowcollector.restservice.collections.RestCollectionsService;
import io.wowcollector.restserviceimpl.services.collections.aggregator.AchievementAggregator;
import io.wowcollector.restserviceimpl.services.collections.aggregator.MountAggregator;
import io.wowcollector.service.http.BattleNetHttpService;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

public class RestCollectionsServiceImpl implements RestCollectionsService {
    private static final Logger LOGGER = Logger.getLogger(RestCollectionsServiceImpl.class.getName());

    @Inject
    private BattleNetHttpService myBattleNetHttpService;
    @Inject
    private RepositoryFactory myRepositoryFactory;

    @Override
    @CacheResult(cacheName = "getMountCollection")
    public Response getMountCollection(String region, String realm, String character) {
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

            return Response.status(Response.Status.OK)
                    .entity(new Gson().toJson(CharacterMountCollectionResponse.newBuilder()
                                                      .withMountCategories(getMountAggregator(region, realm,
                                                                                              character,
                                                                                              battleNetCharacter)
                                                                                   .getMountCollection())
                                                      .build()))
                    .build();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Override
    @CacheResult(cacheName = "getAchievementCollection")
    public Response getAchievementCollection(String region, String realm, String character, String rootCategory) {
        try {
            AchievementAggregator aggregator = getAchievementAggregator(region, realm,
                                                                        character);
            if (aggregator == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Not found")
                        .build();
            }
            return Response.status(Response.Status.OK)
                    .entity(new Gson().toJson(CharacterAchievementCollectionResponse.newBuilder()
                                                      .withAchievementCategories(aggregator
                                                                                         .getAchievementCollection(rootCategory))
                                                      .build()))
                    .build();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @Override
    public Response getToyCollection(String region, String realm, String character) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Not implemented")
                .build();
    }

    private AchievementAggregator getAchievementAggregator(String region, String realm, String character) {
        BattleNetCharacterAchievements achievements =
                myBattleNetHttpService.getCharacterAchievements(BlizzardRegion.get(region),
                                                                realm, character);

        return achievements == null ? null : new AchievementAggregator(myRepositoryFactory, achievements);
    }

    private MountAggregator getMountAggregator(String region, String realm, String character,
                                               BattleNetCharacter battleNetCharacter) {
        BattleNetUserMountCollection collection = myBattleNetHttpService.getUserMountCollection(
                BlizzardRegion.get(region),
                realm,
                character);
        MountCollectionViewDocument document = myRepositoryFactory.of(MountCollectionViewRepository.class)
                .getDefault();
        return new MountAggregator(myRepositoryFactory,
                                   collection,
                                   battleNetCharacter,
                                   document);
    }
}
