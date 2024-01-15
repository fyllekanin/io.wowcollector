package io.wowcollector.entitityview.repository.collectionview;

import io.wowcollector.entityview.repository.collectionview.CollectionMountCategory;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionMountCategoryTest {
    @Test
    public void shouldBeSuccessfulToBuild() {
        // When
        CollectionMountCategory result = getDocument();

        // Then
        assertEquals(result.getId(), 5);
    }

    @Test
    public void shouldContainOriginalValues() {
        // When
        CollectionMountCategory result = getDocument();
        CollectionMountCategory copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getId(), copy.getId());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(CollectionMountCategory.class)
                .verify();
    }

    private CollectionMountCategory getDocument() {
        return CollectionMountCategory
                .newBuilder()
                .withId(5)
                .withAsset("asset")
                .build();
    }
}
