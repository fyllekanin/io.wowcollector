package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.entityview.http.battlenet.BattleNetToken;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetTokenTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetToken result = BattleNetToken.newBuilder()
                .withAccessToken("token")
                .withExpiresIn(5)
                .build();

        // Then
        assertEquals(result.getAccessToken(), "token");
        assertEquals(result.getExpiresIn(), 5);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetToken.class)
                .verify();
    }
}
