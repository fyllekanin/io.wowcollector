package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievement;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetCharacterAchievementTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetCharacterAchievement result = BattleNetCharacterAchievement.newBuilder()
                .withId(5)
                .withCompletedTimestamp(10)
                .build();

        // Then
        assertEquals(result.getId(), 5);
        assertEquals(result.getCompletedTimestamp(), 10);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetCharacterAchievement.class)
                .verify();
    }
}
