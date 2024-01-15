package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.lang.Collections;
import io.wowcollector.entityview.http.battlenet.BattleNetCreatureDisplay;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetCreatureDisplayTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetCreatureDisplay result = BattleNetCreatureDisplay.newBuilder()
                .withAssets(Collections.emptyList())
                .build();

        // Then
        assertTrue(result.getAssets().isEmpty());
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetCreatureDisplay.class).verify();
    }
}
