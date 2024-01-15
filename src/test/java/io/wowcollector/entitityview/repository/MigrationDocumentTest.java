package io.wowcollector.entitityview.repository;

import io.wowcollector.entityview.repository.MigrationDocument;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MigrationDocumentTest {
    @Test
    public void shouldBeSuccessfulToBuild() {
        // Given
        ObjectId objectId = ObjectId.get();

        // When
        MigrationDocument result = getDocument(objectId);

        // Then
        assertEquals(result.getObjectId(), objectId);
        assertEquals(result.getName(), "name");
    }

    @Test
    public void shouldContainOriginalValues() {
        // Given
        ObjectId objectId = ObjectId.get();

        // When
        MigrationDocument result = getDocument(objectId);
        MigrationDocument copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getObjectId(), copy.getObjectId());
        assertEquals(result.getName(), copy.getName());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(MigrationDocument.class)
                .verify();
    }

    private MigrationDocument getDocument(ObjectId objectId) {
        return MigrationDocument
                .newBuilder()
                .withObjectId(objectId)
                .withName("name")
                .build();
    }
}
