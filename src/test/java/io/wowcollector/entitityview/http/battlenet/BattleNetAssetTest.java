package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.common.data.BlizzardAssetKey;
import io.wowcollector.entityview.http.battlenet.BattleNetAsset;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetAssetTest {

    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetAsset result = BattleNetAsset.newBuilder()
                .withKey(BlizzardAssetKey.AVATAR)
                .withValue("value")
                .build();

        // Then
        assertEquals(result.getKey(), BlizzardAssetKey.AVATAR);
        assertEquals(result.getValue(), "value");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetAsset.class)
                .verify();
    }
}
