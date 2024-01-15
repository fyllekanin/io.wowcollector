package io.wowcollector.entitityview.http.battlenet.achievement;

import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementCategory;
import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementIndex;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BattleNetAchievementIndexTest {
    @Test
    public void shouldBePossibleToBuild() {
        // Given
        BattleNetAchievementCategory parent = BattleNetAchievementCategory.newBuilder()
                .build();
        // When
        BattleNetAchievementIndex result = BattleNetAchievementIndex.newBuilder()
                .withAchievements(Collections.emptyList())
                .withParentCategory(parent)
                .withDisplayOrder(5)
                .build();

        // Then
        assertTrue(result.getAchievements()
                           .isEmpty());
        assertEquals(result.getParentCategory(), parent);
        assertEquals(result.getDisplayOrder(), 5);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetAchievementIndex.class)
                .verify();
    }
}
