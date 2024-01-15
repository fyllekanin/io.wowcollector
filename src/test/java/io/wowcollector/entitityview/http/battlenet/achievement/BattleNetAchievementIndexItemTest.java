package io.wowcollector.entitityview.http.battlenet.achievement;

import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievementIndexItem;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetAchievementIndexItemTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetAchievementIndexItem result = BattleNetAchievementIndexItem.newBuilder()
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
                .forClass(BattleNetAchievementIndexItem.class)
                .verify();
    }
}
