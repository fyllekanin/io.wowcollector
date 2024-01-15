package io.wowcollector.entitityview.http.battlenet;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.lang.Collections;
import io.wowcollector.entityview.http.battlenet.BattleNetUserMountCollection;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetUserMountCollectionTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetUserMountCollection result = BattleNetUserMountCollection.newBuilder()
                .withMounts(Collections.emptyList())
                .build();

        // Then
        assertTrue(result.getMounts().isEmpty());
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple().forClass(BattleNetUserMountCollection.class).verify();
    }
}
