package io.wowcollector.entitityview.repository.collectionview;

import io.wowcollector.entityview.repository.collectionview.CollectionCategory;
import io.wowcollector.entityview.repository.collectionview.CollectionMountCategory;
import io.wowcollector.entityview.repository.collectionview.MountCollectionViewDocument;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MountCollectionViewDocumentTest {
    @Test
    public void shouldBeSuccessfulToBuild() {
        // Given
        List<CollectionCategory> categories = Collections.emptyList();

        // When
        MountCollectionViewDocument result = getDocument(categories);

        // Then
        assertEquals(result.getName(), "name");
        assertTrue(result.isDefault());
        assertEquals(result.getCategories(), categories);
        assertEquals(result.getAuthor(), "author");
        assertTrue(result.isUnknownIncluded());
    }

    @Test
    public void shouldContainOriginalValues() {
        // Given
        List<CollectionCategory> categories = Collections.emptyList();

        // When
        MountCollectionViewDocument result = getDocument(categories);
        MountCollectionViewDocument copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getName(), copy.getName());
        assertEquals(result.isDefault(), copy.isDefault());
        assertEquals(result.getCategories(), copy.getCategories());
        assertEquals(result.getAuthor(), copy.getAuthor());
        assertEquals(result.isUnknownIncluded(), copy.isUnknownIncluded());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(CollectionMountCategory.class)
                .verify();
    }

    private MountCollectionViewDocument getDocument(List<CollectionCategory> categories) {
        return MountCollectionViewDocument
                .newBuilder()
                .withName("name")
                .withIsDefault(true)
                .withCategories(categories)
                .withAuthor("author")
                .withIsUnknownIncluded(true)
                .build();
    }
}
