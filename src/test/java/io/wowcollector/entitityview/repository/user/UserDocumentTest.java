package io.wowcollector.entitityview.repository.user;

import io.wowcollector.entityview.repository.user.UserCharacter;
import io.wowcollector.entityview.repository.user.UserDocument;
import io.wowcollector.entityview.repository.user.UserSettings;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDocumentTest {
    @Test
    public void shouldBeSuccessfulToBuild() {
        // Given
        ObjectId objectId = ObjectId.get();
        List<UserCharacter> characters = Collections.emptyList();
        UserSettings settings = UserSettings.newBuilder()
                .build();

        // When
        UserDocument result = getDocument(characters, settings, objectId);

        // Then
        assertEquals(result.getObjectId(), objectId);
        assertEquals(result.getCharacters(), characters);
        assertEquals(result.getSettings(), settings);
    }

    @Test
    public void shouldContainOriginalValues() {
        // Given
        ObjectId objectId = ObjectId.get();
        List<UserCharacter> characters = Collections.emptyList();
        UserSettings settings = UserSettings.newBuilder()
                .build();

        // When
        UserDocument result = getDocument(characters, settings, objectId);
        UserDocument copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getObjectId(), copy.getObjectId());
        assertEquals(result.getCharacters(), copy.getCharacters());
        assertEquals(result.getSettings(), copy.getSettings());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(UserDocument.class)
                .verify();
    }

    private UserDocument getDocument(List<UserCharacter> characters, UserSettings settings, ObjectId objectId) {
        return UserDocument
                .newBuilder()
                .withObjectId(objectId)
                .withBattleTag("battleTag")
                .withCharacters(characters)
                .withSettings(settings)
                .build();
    }
}
