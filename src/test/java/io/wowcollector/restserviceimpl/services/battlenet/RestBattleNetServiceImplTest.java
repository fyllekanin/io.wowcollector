package io.wowcollector.restserviceimpl.services.battlenet;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import io.quarkus.test.junit.QuarkusTest;
import io.wowcollector.entityview.response.collection.RegionRealmResponse;
import jakarta.ws.rs.core.Response;

/**
 * Tests for {@link RestBattleNetServiceImpl}
 */
@QuarkusTest
public class RestBattleNetServiceImplTest {
    @Test
    public void shouldReturnRealmsAndRegions() {
        // Given
        String response = given()
                .when()
                .get("/api/v1/battle-net/realms-regions")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract()
                .body()
                .asString();

        // When
        RegionRealmResponse entity = new Gson().fromJson(response, RegionRealmResponse.class);

        // Then
        assertFalse(entity.getRealms()
                            .isEmpty());
        assertFalse(entity.getRegions()
                            .isEmpty());
    }
}
