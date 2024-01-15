package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetEntityDisplay;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetEntityDisplayTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetEntityDisplay result = BattleNetEntityDisplay.newBuilder()
                .withId(1)
                .build();

        // Then
        assertEquals(result.getId(), 1);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetEntityDisplay.class)
                .verify();
    }
}
