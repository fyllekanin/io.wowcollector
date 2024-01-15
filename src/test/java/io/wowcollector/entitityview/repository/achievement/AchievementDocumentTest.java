package io.wowcollector.entitityview.repository.achievement;

import io.wowcollector.entityview.http.battlenet.BattleNetMedia;
import io.wowcollector.entityview.repository.achievement.AchievementDocument;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AchievementDocumentTest {
    @Test
    public void shouldBeSuccessfulToBuild() {
        // Given
        ObjectId objectId = ObjectId.get();
        BattleNetMedia media = BattleNetMedia.newBuilder()
                .build();

        // When
        AchievementDocument result = getDocument(media, objectId);

        // Then
        assertEquals(result.getObjectId(), objectId);
        assertEquals(result.getId(), 5);
        assertEquals(result.getName(), "name");
        assertEquals(result.getDescription(), "description");
        assertEquals(result.getPoints(), 10);
        assertTrue(result.isAccountWide());
        assertEquals(result.getMedia(), media);
        assertEquals(result.getDisplayOrder(), 15);
        assertEquals(result.getCategoryId(), 20);
    }

    @Test
    public void shouldContainOriginalValues() {
        // Given
        ObjectId objectId = ObjectId.get();
        BattleNetMedia media = BattleNetMedia.newBuilder()
                .build();

        // When
        AchievementDocument result = getDocument(media, objectId);
        AchievementDocument copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getObjectId(), copy.getObjectId());
        assertEquals(result.getId(), copy.getId());
        assertEquals(result.getName(), copy.getName());
        assertEquals(result.getDescription(), copy.getDescription());
        assertEquals(result.getPoints(), copy.getPoints());
        assertEquals(result.isAccountWide(), copy.isAccountWide());
        assertEquals(result.getMedia(), copy.getMedia());
        assertEquals(result.getDisplayOrder(), copy.getDisplayOrder());
        assertEquals(result.getCategoryId(), copy.getCategoryId());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(AchievementDocument.class)
                .verify();
    }

    private AchievementDocument getDocument(BattleNetMedia media, ObjectId objectId) {
        return AchievementDocument
                .newBuilder()
                .withObjectId(objectId)
                .withId(5)
                .withName("name")
                .withDescription("description")
                .withPoints(10)
                .withIsAccountWide(true)
                .withMedia(media)
                .withDisplayOrder(15)
                .withCategoryId(20)
                .build();
    }
}
