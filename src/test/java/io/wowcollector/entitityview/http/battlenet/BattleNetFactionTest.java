package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetFactionTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetFaction result = BattleNetFaction.newBuilder()
                .withType("type")
                .withName("name")
                .build();

        // Then
        assertEquals(result.getType(), "type");
        assertEquals(result.getName(), "name");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetFaction.class).verify();
    }
}
