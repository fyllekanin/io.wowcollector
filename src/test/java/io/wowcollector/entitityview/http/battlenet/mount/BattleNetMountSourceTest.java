package io.wowcollector.entitityview.http.battlenet.mount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountSource;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetMountSourceTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetMountSource result = BattleNetMountSource.newBuilder()
                .withType("type")
                .withName("name")
                .build();

        // Then
        assertEquals(result.getType(), "type");
        assertEquals(result.getName(), "name");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetMountSource.class)
                .verify();
    }
}
