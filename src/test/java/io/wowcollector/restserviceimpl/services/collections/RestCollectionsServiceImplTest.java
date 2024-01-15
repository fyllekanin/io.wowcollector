package io.wowcollector.restserviceimpl.services.collections;

import com.google.gson.Gson;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.MockitoConfig;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacter;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterActiveSpec;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterClass;
import io.wowcollector.entityview.http.battlenet.BattleNetCharacterRace;
import io.wowcollector.entityview.http.battlenet.BattleNetFaction;
import io.wowcollector.entityview.http.battlenet.BattleNetGender;
import io.wowcollector.entityview.http.battlenet.realm.BattleNetRealm;
import io.wowcollector.entityview.http.battlenet.BattleNetUserMountCollection;
import io.wowcollector.entityview.response.collection.CharacterMountCollectionResponse;
import io.wowcollector.service.http.BattleNetHttpService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests for {@link RestCollectionsServiceImpl}
 */
@QuarkusTest
public class RestCollectionsServiceImplTest {
    private static final String CHARACTER_NAME = "Thedrunken";

    @InjectMock()
    @MockitoConfig(convertScopes = true)
    BattleNetHttpService myBattleNetHttpService;

    @Test
    public void shouldReturnACorrectCollection() {
        // Given
        Mockito.when(myBattleNetHttpService.getCharacterMedia(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Optional.empty());
        Mockito.when(myBattleNetHttpService.getUserMountCollection(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(BattleNetUserMountCollection.newBuilder()
                                    .withMounts(Collections.emptyList())
                                    .build());
        Mockito.when(myBattleNetHttpService.getCharacter(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(getMockCharacter());

        String response = given()
                .when()
                .get("/api/v1/collections/eu/kazzak/thedrunken/mounts")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .body()
                .asString();

        // When
        CharacterMountCollectionResponse entity = new Gson().fromJson(response, CharacterMountCollectionResponse.class);

        // Then
        assertFalse(entity.getMountCategories()
                            .isEmpty());
    }

    @Test
    public void shouldReturnBadRequestIfInvalidRegion() {
        given()
                .when()
                .get("/api/v1/collections/invalid/kazzak/thedrunken/mounts")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void shouldReturnBadRequestIfInvalidRealms() {
        // Given
        Mockito.when(myBattleNetHttpService.getCharacter(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(null);

        // When & Then
        given()
                .when()
                .get("/api/v1/collections/eu/invalid/thedrunken/mounts")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundIfInvalidCharacter() {
        // Given
        Mockito.when(myBattleNetHttpService.getCharacter(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(null);

        // When & Then
        given()
                .when()
                .get("/api/v1/collections/eu/kazzak/this-is-not-possible/mounts")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    private BattleNetCharacter getMockCharacter() {
        return BattleNetCharacter.newBuilder()
                .withName(CHARACTER_NAME)
                .withFaction(BattleNetFaction.newBuilder()
                                     .withType("HORDE")
                                     .build())
                .withRealm(BattleNetRealm.newBuilder()
                                   .withSlug("kazzak")
                                   .build())
                .withRace(BattleNetCharacterRace.newBuilder()
                                  .build())
                .withClazz(BattleNetCharacterClass.newBuilder()
                                   .build())
                .withGender(BattleNetGender.newBuilder()
                                    .build())
                .withActiveSpec(BattleNetCharacterActiveSpec.newBuilder()
                                        .build())
                .build();
    }
}
