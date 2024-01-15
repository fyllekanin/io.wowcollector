package io.wowcollector.restserviceimpl.services.collections;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import io.quarkus.test.junit.QuarkusTest;
import io.wowcollector.entityview.response.collection.CharacterMountCollectionResponse;
import jakarta.ws.rs.core.Response;

/**
 * Tests for {@link RestCollectionsServiceImpl}
 */
@QuarkusTest
public class RestCollectionsServiceImplTest {

    @Test
    public void shouldReturnACorrectCollection() {
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
        // When & Then
        given()
                .when()
                .get("/api/v1/collections/eu/invalid/thedrunken/mounts")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundIfInvalidCharacter() {
        // When & Then
        given()
                .when()
                .get("/api/v1/collections/eu/kazzak/this-is-not-possible/mounts")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
