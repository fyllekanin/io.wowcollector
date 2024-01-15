package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.BattleNetOauth;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetOauthTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetOauth result = BattleNetOauth.newBuilder()
                .withAccessToken("token")
                .withTokenType("type")
                .build();

        // Then
        assertEquals(result.getAccessToken(), "token");
        assertEquals(result.getTokenType(), "type");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetOauth.class).verify();
    }
}
