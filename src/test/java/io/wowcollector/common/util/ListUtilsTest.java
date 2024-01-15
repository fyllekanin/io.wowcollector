package io.wowcollector.common.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ListUtilsTest {

    @Test
    public void shouldReturnOnlyOneChunk() {
        // Given
        List<String> items = Arrays.asList(new String[50]);

        // When
        List<List<String>> result = ListUtils.getChunks(50, items);

        // Then
        assertTrue(result.size() == 1, "Expected 1 chunk");
    }

    @Test
    public void shouldReturnFiveChunks() {
        // Given
        List<String> items = Arrays.asList(new String[50]);

        // When
        List<List<String>> result = ListUtils.getChunks(10, items);

        // Then
        assertTrue(result.size() == 5, "Expected 5 chunks");
    }
}
