package io.wowcollector.restservice.collections;

import io.wowcollector.entityview.response.collection.CharacterAchievementCollectionResponse;
import io.wowcollector.entityview.response.collection.CharacterMountCollectionResponse;
import io.wowcollector.entityview.response.collection.toy.ToyResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/api/v1/collections")
@Tags(value = @Tag(name = "Collections"))
public interface RestCollectionsService {

    @GET
    @Path("/{region}/{realm}/{character}/mounts")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
            CharacterMountCollectionResponse.class)))
    Response getMountCollection(@PathParam("region") String region, @PathParam("realm") String realm,
                                @PathParam("character") String character);

    @GET
    @Path("/{region}/{realm}/{character}/achievements")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
            CharacterAchievementCollectionResponse.class)))
    @Parameter(name = "rootCategory",
            in = ParameterIn.QUERY,
            description = "If provided, only given root category will be returned")
    Response getAchievementCollection(@PathParam("region") String region, @PathParam("realm") String realm,
                                      @PathParam("character") String character,
                                      @QueryParam("rootCategory") String rootCategory);

    @GET
    @Path("/{region}/{realm}/{character}/toys")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
            ToyResponse.class)))
    Response getToyCollection(@PathParam("region") String region, @PathParam("realm") String realm,
                              @PathParam("character") String character);
}
