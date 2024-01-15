package io.wowcollector.entitityview.http.battlenet.achievement;

import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementCategory;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementCategoryIndex;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetAchievementCategoryIndexTest {
    @Test
    public void shouldBePossibleToBuild() {
        // Given
        List<BattleNetAchievementCategory> categories =
                Collections.singletonList(BattleNetAchievementCategory.newBuilder()
                                                  .build());
        List<BattleNetAchievementCategory> rootCategories =
                Collections.singletonList(BattleNetAchievementCategory.newBuilder()
                                                  .build());

        // When
        BattleNetAchievementCategoryIndex result = BattleNetAchievementCategoryIndex.newBuilder()
                .withCategories(categories)
                .withRootCategories(rootCategories)
                .build();

        // Then
        assertEquals(result.getCategories(), categories);
        assertEquals(result.getRootCategories(), categories);
        assertEquals(result.getAllCategories()
                             .size(), 2);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetAchievementCategoryIndex.class)
                .verify();
    }
}
