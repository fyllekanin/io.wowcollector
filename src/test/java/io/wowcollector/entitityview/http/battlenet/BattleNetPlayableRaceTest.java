package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetPlayableRace;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetPlayableRaceTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetPlayableRace result = BattleNetPlayableRace.newBuilder()
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
                .forClass(BattleNetPlayableRace.class)
                .verify();
    }
}
