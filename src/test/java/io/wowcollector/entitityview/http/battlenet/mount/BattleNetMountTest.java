package io.wowcollector.entitityview.http.battlenet.mount;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.lang.Collections;
import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import io.wowcollector.entityview.http.battlenet.BattleNetMount;
import io.wowcollector.entityview.http.battlenet.mount.BattleNetMountSource;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetMountTest {
    @Test
    public void shouldBePossibleToBuild() {
        // Given
        BattleNetMountSource source = BattleNetMountSource.newBuilder()
                .build();
        BattleNetFaction faction = BattleNetFaction.newBuilder()
                .build();

        // When
        BattleNetMount result = BattleNetMount.newBuilder()
                .withId(1)
                .withDescription("desc")
                .withSource(source)
                .withFaction(faction)
                .withCreatureDisplays(Collections.emptyList())
                .withShouldExcludeIfUncollected(true)
                .build();

        // Then
        assertEquals(result.getId(), 1);
        assertEquals(result.getDescription(), "desc");
        assertEquals(result.getSource(), source);
        assertEquals(result.getFaction(), faction);
        assertEquals(result.getCreatureDisplays()
                             .isEmpty(), true);
        assertEquals(result.getShouldExcludeIfUncollected(), true);
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetMount.class)
                .verify();
    }
}
