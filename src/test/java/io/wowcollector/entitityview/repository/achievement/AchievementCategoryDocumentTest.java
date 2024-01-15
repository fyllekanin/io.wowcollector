package io.wowcollector.entitityview.repository.achievement;

import io.wowcollector.entityview.repository.achievement.AchievementCategoryDocument;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AchievementCategoryDocumentTest {

    @Test
    public void shouldBeSuccessfulToBuild() {
        // Given
        ObjectId objectId = ObjectId.get();

        // When
        AchievementCategoryDocument result = getDocument(objectId);

        // Then
        assertEquals(result.getObjectId(), objectId);
        assertEquals(result.getId(), 1);
        assertEquals(result.getName(), "name");
        assertTrue(result.isRootCategory());
        assertEquals(result.getRootCategoryId(), 5);
        assertEquals(result.getDisplayOrder(), 10);
    }

    @Test
    public void shouldContainOriginalValues() {
        // Given
        ObjectId objectId = ObjectId.get();

        // When
        AchievementCategoryDocument result = getDocument(objectId);
        AchievementCategoryDocument copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getObjectId(), copy.getObjectId());
        assertEquals(result.getId(), copy.getId());
        assertEquals(result.getName(), copy.getName());
        assertEquals(result.isRootCategory(), copy.isRootCategory());
        assertEquals(result.getRootCategoryId(), copy.getRootCategoryId());
        assertEquals(result.getDisplayOrder(), copy.getDisplayOrder());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(AchievementCategoryDocument.class)
                .verify();
    }

    private AchievementCategoryDocument getDocument(ObjectId objectId) {
        return AchievementCategoryDocument
                .newBuilder()
                .withObjectId(objectId)
                .withId(1)
                .withName("name")
                .withIsRootCategory(true)
                .withRootCategoryId(5)
                .withDisplayOrder(10)
                .build();
    }
}
