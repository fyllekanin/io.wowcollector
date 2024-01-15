package io.wowcollector.service.scheduler.task;

import io.wowcollector.common.data.BlizzardRegion;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievement;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementCategory;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementCategoryIndex;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementIndex;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementIndexItem;
import io.wowcollector.entityview.repository.achievement.AchievementCategoryDocument;
import io.wowcollector.entityview.repository.achievement.AchievementDocument;
import io.wowcollector.repository.RepositoryFactory;
import io.wowcollector.repository.repositories.achievement.AchievementCategoryRepository;
import io.wowcollector.repository.repositories.achievement.AchievementRepository;
import io.wowcollector.service.http.BattleNetHttpService;

import java.util.logging.Logger;

public class ScanAchievements implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(ScanAchievements.class.getName());

    private final BlizzardRegion myRegion;
    private final BattleNetHttpService myBattleNetHttpService;

    private final AchievementCategoryRepository myAchievementCategoryRepository;
    private final AchievementRepository myAchievementRepository;


    public ScanAchievements(BlizzardRegion region, RepositoryFactory repositoryFactory,
                            BattleNetHttpService battleNetService) {
        myRegion = region;
        myBattleNetHttpService = battleNetService;

        myAchievementCategoryRepository = repositoryFactory.of(AchievementCategoryRepository.class);
        myAchievementRepository = repositoryFactory.of(AchievementRepository.class);
    }

    @Override
    public void run() {
        try {
            LOGGER.info(String.format("Starting: scan of achievements for region %s", myRegion.getRegion()));
            BattleNetAchievementCategoryIndex categoryIndex =
                    myBattleNetHttpService.getAchievementCategoryIndex(myRegion);

            categoryIndex.getAllCategories()
                    .forEach(this::runCategory);

            LOGGER.info(String.format("Done: scan of achievements for region %s", myRegion.getRegion()));
        } catch (Exception e) {
            LOGGER.severe(String.format("Failed: scan of achievements, reason: %s", e.getMessage()));
        }
    }

    private void runCategory(BattleNetAchievementCategory category) {
        BattleNetAchievementIndex achievementIndex = myBattleNetHttpService.getAchievementCategory(myRegion,
                                                                                                   category.getId());
        if (achievementIndex == null) {
            LOGGER.severe(String.format("Achievement index with id \"%d\" and name \"%s\" was null",
                                        category.getId()
                    , category.getName()));
            return;
        }

        AchievementCategoryDocument categoryDocument = AchievementCategoryDocument
                .newBuilder()
                .withId(category.getId())
                .withName(category.getName())
                .withIsRootCategory(achievementIndex.getParentCategory() == null)
                .withRootCategoryId(achievementIndex.getParentCategory() == null ? -1 :
                                            achievementIndex.getParentCategory()
                                                    .getId())
                .withDisplayOrder(achievementIndex.getDisplayOrder())
                .build();
        AchievementCategoryDocument existing = myAchievementCategoryRepository.getById(category.getId());

        if (existing == null) {
            myAchievementCategoryRepository.create(categoryDocument);
        } else if (!existing.newBuilderFromCurrent()
                .withObjectId(null)
                .build()
                .equals(categoryDocument)) {
            myAchievementCategoryRepository.update(categoryDocument.getId(), categoryDocument);
        }

        if (achievementIndex.getAchievements() != null) {
            achievementIndex.getAchievements()
                    .forEach(item -> runAchievement(category, item));
        }
    }

    private void runAchievement(BattleNetAchievementCategory category, BattleNetAchievementIndexItem indexItem) {
        BattleNetAchievement achievement = myBattleNetHttpService.getAchievement(myRegion,
                                                                                 indexItem.getId());
        BattleNetMedia media = myBattleNetHttpService.getAchievementMedia(myRegion,
                                                                          indexItem.getId());
        if (achievement == null) {
            LOGGER.severe(String.format("Achievement with id \"%d\" and name \"%s\" was null",
                                        indexItem.getId()
                    , indexItem.getName()));
            return;
        }

        AchievementDocument achievementDocument = AchievementDocument.newBuilder()
                .withId(achievement.getId())
                .withName(achievement.getName())
                .withDescription(achievement.getDescription())
                .withPoints(achievement.getPoints())
                .withIsAccountWide(achievement.isAccountWide())
                .withMedia(media)
                .withDisplayOrder(achievement.getDisplayOrder())
                .withCategoryId(category.getId())
                .build();

        AchievementDocument existingAchievement = myAchievementRepository.getById(achievementDocument.getId());

        if (existingAchievement == null) {
            myAchievementRepository.create(achievementDocument);
        } else if (!existingAchievement.newBuilderFromCurrent()
                .withObjectId(null)
                .build()
                .equals(achievementDocument)) {
            myAchievementRepository.update(achievementDocument.getId(), achievementDocument);
        }
    }
}
