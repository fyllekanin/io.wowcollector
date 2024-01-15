package io.wowcollector.entitityview.http.battlenet.realm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.lang.Collections;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealms;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetRealmsTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetRealms result = BattleNetRealms.newBuilder()
                .withRealms(Collections.emptyList())
                .build();

        // Then
        assertTrue(result.getRealms()
                           .isEmpty());
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetRealms.class)
                .verify();
    }
}
