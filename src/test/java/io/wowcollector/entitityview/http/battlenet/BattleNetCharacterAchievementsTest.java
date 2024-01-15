package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetCharacterAchievements;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BattleNetCharacterAchievementsTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetCharacterAchievements result = BattleNetCharacterAchievements.newBuilder()
                .withAchievements(Collections.emptyList())
                .build();

        // Then
        assertTrue(result.getAchievements()
                           .isEmpty());
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetCharacterAchievements.class)
                .verify();
    }
}
