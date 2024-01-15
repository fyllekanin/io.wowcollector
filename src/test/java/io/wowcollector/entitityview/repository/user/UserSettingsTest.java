package io.wowcollector.entitityview.repository.user;

import io.wowcollector.entityview.repository.user.UserSettings;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserSettingsTest {
    @Test
    public void shouldBeSuccessfulToBuild() {
        // When
        UserSettings result = getDocument();

        // Then
        assertEquals(result.getDefaultView(), "default");
        assertEquals(result.getDefaultCharacterId(), 5);
    }

    @Test
    public void shouldContainOriginalValues() {
        // When
        UserSettings result = getDocument();
        UserSettings copy = result.newBuilderFromCurrent()
                .build();

        // Then
        assertEquals(result.getDefaultView(), copy.getDefaultView());
        assertEquals(result.getDefaultCharacterId(), copy.getDefaultCharacterId());
    }

    @Test
    public void shouldVerifyEqualsAndHas() {
        EqualsVerifier.simple()
                .forClass(UserSettings.class)
                .verify();
    }

    private UserSettings getDocument() {
        return UserSettings
                .newBuilder()
                .withDefaultView("default")
                .withDefaultCharacterId(5)
                .build();
    }
}
