package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetCharacterActiveSpec;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetCharacterActiveSpecTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetCharacterActiveSpec result = BattleNetCharacterActiveSpec.newBuilder()
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
                .forClass(BattleNetCharacterActiveSpec.class)
                .verify();
    }
}
