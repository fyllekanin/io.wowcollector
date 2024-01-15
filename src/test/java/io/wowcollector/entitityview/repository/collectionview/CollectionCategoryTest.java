package io.wowcollector.entitityview.repository.collectionview;

import io.wowcollector.entityview.repository.collectionview.CollectionCategory;
import io.wowcollector.entityview.repository.collectionview.CollectionMountCategory;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionCategoryTest {
    @Test
    public void shouldBeSuccessfulToBuild() {
        // Given
        List<CollectionMountCategory> mounts = Collections.emptyList();
        List<CollectionCategory> categories = Collections.emptyList();

        // When
        CollectionCategory result = getDocument(mounts, categories);

        // Then
        assertEquals(result.getParent(), "parent");
        assertEquals(result.getName(), "name");
        assertEquals(result.getOrder(), 5);
        assertEquals(result.getMounts(), mounts);
        assertEquals(result.getCategories(), categories);
    }

    @Test
    public void shouldContainOriginalValues() {
        // Given
        List<CollectionMountCategory> mounts = Collections.emptyList();
        List<CollectionCategory> categories = Collections.emptyList();

        // When
        CollectionCategory result = getDocument(mounts, categories);
        CollectionCategory copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getParent(), copy.getParent());
        assertEquals(result.getName(), copy.getName());
        assertEquals(result.getOrder(), copy.getOrder());
        assertEquals(result.getMounts(), copy.getMounts());
        assertEquals(result.getCategories(), copy.getCategories());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(CollectionCategory.class)
                .withPrefabValues(CollectionCategory.class, Mockito.mock(CollectionCategory.class),
                                  Mockito.mock(CollectionCategory.class))
                .verify();
    }

    private CollectionCategory getDocument(List<CollectionMountCategory> mounts, List<CollectionCategory> categories) {
        return CollectionCategory
                .newBuilder()
                .withParent("parent")
                .withName("name")
                .withOrder(5)
                .withMounts(mounts)
                .withCategories(categories)
                .build();
    }
}
