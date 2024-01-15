package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.lang.Collections;
import io.wowcollector.entityview.http.battlenet.BattleNetProfileWow;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetProfileWowTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetProfileWow result = BattleNetProfileWow.newBuilder()
                .withWowAccounts(Collections.emptyList())
                .build();

        // Then
        assertTrue(result.getWoWAccounts().isEmpty());
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetProfileWow.class).verify();
    }
}
