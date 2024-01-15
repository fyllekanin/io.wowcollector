package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.lang.Collections;
import io.wowcollector.entityview.http.battlenet.BattleNetWowAccount;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetWowAccountTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetWowAccount result = BattleNetWowAccount.newBuilder()
                .withWowAccounts(Collections.emptyList())
                .build();

        // Then
        assertTrue(result.getCharacters().isEmpty());
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetWowAccount.class).verify();
    }
}
