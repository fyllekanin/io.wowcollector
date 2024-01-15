package io.wowcollector.entitityview.http.battlenet.achievement;

import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementCategory;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetAchievementCategoryTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetAchievementCategory result = BattleNetAchievementCategory.newBuilder()
                .withId(5)
                .withName("name")
                .build();

        // Then
        assertEquals(result.getId(), 5);
        assertEquals(result.getName(), "name");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetAchievementCategory.class)
                .verify();
    }
}
