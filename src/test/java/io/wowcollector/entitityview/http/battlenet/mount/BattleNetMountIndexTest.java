package io.wowcollector.entitityview.http.battlenet.mount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountIndex;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetMountIndexTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetMountIndex result = BattleNetMountIndex.newBuilder()
                .withName("name")
                .withId(1)
                .build();

        // Then
        assertEquals(result.getName(), "name");
        assertEquals(result.getId(), 1);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetMountIndex.class)
                .verify();
    }
}
