package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.BattleNetUserMountDetails;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetUserMountDetailsTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetUserMountDetails result = BattleNetUserMountDetails.newBuilder()
                .withName("name")
                .withId(1)
                .build();

        // Then
        assertEquals(result.getName(), "name");
        assertEquals(result.getId(), 1);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetUserMountDetails.class).verify();
    }
}
