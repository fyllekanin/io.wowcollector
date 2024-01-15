package io.wowcollector.entitityview.http.battlenet;

import io.wowcollector.common.data.BlizzardAssetKey;
import io.wowcollector.entityview.http.battlenet.BattleNetAsset;
import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BattleNetCharacterMediaTest {
    @Test
    public void shouldBePossibleToBuild() {
        // When
        BattleNetMedia result = BattleNetMedia.newBuilder()
                .withId(5)
                .withAssets(List.of(BattleNetAsset.newBuilder()
                                            .withKey(BlizzardAssetKey.AVATAR)
                                            .withValue("value")
                                            .build()))
                .build();

        // Then
        assertEquals(result.getAssets()
                             .size(), 1);
        assertEquals(result.getId(), 5);
        assertEquals(result.getAssetWithKey(BlizzardAssetKey.AVATAR), "value");
    }

    @Test
    public void shouldBeEquals() {
        EqualsVerifier.simple()
                .forClass(BattleNetMedia.class)
                .verify();
    }
}
