package io.wowcollector.restserviceimpl.services.collections.aggregator;

import io.wowcollector.common.data.BlizzardAssetKey;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievement;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievements;
import io.wowcollector.entityview.repository.achievement.AchievementCategoryDocument;
import io.wowcollector.entityview.response.collection.achievement.AchievementCategoryResponse;
import io.wowcollector.entityview.response.collection.achievement.AchievementResponse;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.achievement.AchievementCategoryRepository;
import io.wowcollector.repository.repositories.achievement.AchievementRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AchievementAggregator {
    private final List<Long> myCollectedIds;
    private final AchievementCategoryRepository myAchievementCategoryRepository;
    private final AchievementRepository myAchievementRepository;

    public AchievementAggregator(RepositoryFactory repositoryFactory,
                                 BattleNetCharacterAchievements collection) {
        myCollectedIds = collection.getAchievements()
                .stream()
                .map(BattleNetCharacterAchievement::getId)
                .collect(Collectors.toList());

        myAchievementCategoryRepository = repositoryFactory.of(AchievementCategoryRepository.class);
        myAchievementRepository = repositoryFactory.of(AchievementRepository.class);
    }

    public List<AchievementCategoryResponse> getAchievementCollection(String rootCategory) {
        List<AchievementCategoryDocument> categories = myAchievementCategoryRepository.getAll();
        return categories
                .stream()
                .filter(item -> item.isRootCategory() && (rootCategory == null || item.getName()
                        .equals(rootCategory)))
                .map(item -> getCategoryResponse(item, categories))
                .collect(Collectors.toList());
    }

    private AchievementCategoryResponse getCategoryResponse(AchievementCategoryDocument category,
                                                            List<AchievementCategoryDocument> categories) {

        return AchievementCategoryResponse.newBuilder()
                .withName(category.getName())
                .withAchievements(getAchievementsForCategory(category.getId()))
                .withSubCategories(categories.stream()
                                           .filter(item -> category.getId() == item.getRootCategoryId())
                                           .map(item -> getCategoryResponse(item, categories))
                                           .collect(Collectors.toList()))
                .build();
    }

    private List<AchievementResponse> getAchievementsForCategory(long categoryId) {
        return myAchievementRepository.getByCategoryId(categoryId)
                .stream()
                .map(item -> AchievementResponse.newBuilder()
                        .withId(item.getId())
                        .withName(item.getName())
                        .withDescription(item.getDescription())
                        .withPoints(item.getPoints())
                        .withDisplayOrder(item.getDisplayOrder())
                        .withIcon(item.getMedia()
                                          .getAssetWithKey(BlizzardAssetKey.ICON))
                        .withIsCollected(myCollectedIds.contains(item.getId()))
                        .build()
                )
                .collect(Collectors.toList());
    }
}
