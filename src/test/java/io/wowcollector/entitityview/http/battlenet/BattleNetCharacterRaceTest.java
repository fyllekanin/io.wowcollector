package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetCharacterRace;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetCharacterRaceTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetCharacterRace result = BattleNetCharacterRace.newBuilder()
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
                .forClass(BattleNetCharacterRace.class)
                .verify();
    }
}
