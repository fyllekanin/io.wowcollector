package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.BattleNetUserMount;
import io.wowcollector.entityview.http.battlenet.BattleNetUserMountDetails;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetUserMountTest {
    @Test
    public void shouldBePossibleToBuild() {
        // Given
        BattleNetUserMountDetails details = BattleNetUserMountDetails.newBuilder().build();

        // When
        BattleNetUserMount result = BattleNetUserMount.newBuilder()
                .withUserMountDetails(details)
                .build();

        // Then
        assertEquals(result.getUserMountDetails(), details);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetUserMount.class).verify();
    }
}
