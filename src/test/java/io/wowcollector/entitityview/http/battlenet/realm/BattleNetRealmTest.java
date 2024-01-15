package io.wowcollector.entitityview.http.battlenet.realm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;
import nl.jqno.equalsverifier.EqualsVerifier;

public class BattleNetRealmTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetRealm result = BattleNetRealm.newBuilder()
                .withId(1)
                .withName("name")
                .withSlug("slug")
                .build();

        // Then
        assertEquals(result.getId(), 1);
        assertEquals(result.getName(), "name");
        assertEquals(result.getSlug(), "slug");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetRealm.class)
                .verify();
    }
}
