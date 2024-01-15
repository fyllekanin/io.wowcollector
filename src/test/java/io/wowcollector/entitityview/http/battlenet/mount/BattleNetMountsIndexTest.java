package io.wowcollector.entitityview.http.battlenet.mount;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.lang.Collections;
import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountsIndex;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetMountsIndexTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetMountsIndex result = BattleNetMountsIndex.newBuilder()
                .withMounts(Collections.emptyList())
                .build();

        // Then
        assertTrue(result.getMounts()
                           .isEmpty());
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetMountsIndex.class)
                .verify();
    }
}
