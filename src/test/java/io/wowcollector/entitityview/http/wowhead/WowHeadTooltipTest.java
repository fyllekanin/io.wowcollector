package io.wowcollector.entitityview.http.wowhead;

import io.wowcollector.entityview.http.wowhead.WowHeadTooltip;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WowHeadTooltipTest {

    @Test
    public void shouldBuildSuccessfully() {
        // When
        WowHeadTooltip result = WowHeadTooltip.newBuilder()
                .withIcon("icon")
                .build();

        // Then
        assertEquals(result.getIcon(), "icon");
        assertTrue(result.getFullIconUrl()
                           .endsWith("icon.jpg"));
    }

    @Test
    public void shouldTestEqualsAndHash() {
        EqualsVerifier
                .simple()
                .forClass(WowHeadTooltip.class)
                .verify();
    }
}
