package io.wowcollector.restservice.battlenet;

import io.wowcollector.entityview.response.character.CharacterResponse;
import io.wowcollector.entityview.response.collection.RegionRealmResponse;
import io.wowcollector.entityview.response.collection.achievement.AchievementCategoryResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/api/v1/battle-net")
@Tags(value = @Tag(name = "Blizzard Data"))
public interface RestBattleNetService {

    @GET
    @Path("/realms-regions")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RegionRealmResponse.class)))
    Response getRealmsAndRegions();

    @GET
    @Path("/achievement-root-categories")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(allOf = AchievementCategoryResponse.class)))
    Response getAchievementRootCategories();

    @GET
    @Path("/character/{region}/{realm}/{character}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(allOf = CharacterResponse.class)))
    Response getCharacter(String region, String realm, String character);
}
