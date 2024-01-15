package io.wowcollector.entitityview.http.battlenet.achievement;

import io.wowcollector.entityview.http.battlenet.achievement.BattleNetAchievement;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BattleNetAchievementTest {
    @Test
    public void shouldBePossibleToBuild() {
        // Given
        BattleNetMedia media = BattleNetMedia.newBuilder()
                .build();

        // When
        BattleNetAchievement result = BattleNetAchievement.newBuilder()
                .withId(5)
                .withName("name")
                .withDescription("description")
                .withPoints(1)
                .withIsAccountWide(true)
                .withMedia(media)
                .withDisplayOrder(3)
                .build();

        // Then
        assertEquals(result.getId(), 5);
        assertEquals(result.getName(), "name");
        assertEquals(result.getDescription(), "description");
        assertEquals(result.getPoints(), 1);
        assertTrue(result.isAccountWide());
        assertEquals(result.getMedia(), media);
        assertEquals(result.getDisplayOrder(), 3);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetAchievement.class)
                .verify();
    }
}
