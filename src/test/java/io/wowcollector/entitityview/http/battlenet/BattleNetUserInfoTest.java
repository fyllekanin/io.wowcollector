package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.BattleNetUserInfo;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetUserInfoTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetUserInfo result = BattleNetUserInfo.newBuilder()
                .withBattleTag("tag")
                .build();

        // Then
        assertEquals(result.getBattleTag(), "tag");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetUserInfo.class).verify();
    }
}
