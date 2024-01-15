package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetGender;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetGenderTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetGender result = BattleNetGender.newBuilder()
                .withType("type")
                .withName("name")
                .build();

        // Then
        assertEquals(result.getType(), "type");
        assertEquals(result.getName(), "name");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetGender.class)
                .verify();
    }
}
