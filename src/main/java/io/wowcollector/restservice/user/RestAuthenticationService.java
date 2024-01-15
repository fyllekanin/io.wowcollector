package io.wowcollector.restservice.user;

import io.wowcollector.entityview.response.user.AuthorizationResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/api/v1/auth")
@Tags(value = @Tag(name = "Authentication"))
public interface RestAuthenticationService {

    @Path("/redirect")
    @GET
    Response redirect();

    @Path("/oauth")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
            AuthorizationResponse.class)))
    Response oauth(@QueryParam("code") String code);

    @Path("/refresh")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
            AuthorizationResponse.class)))
    Response getRefresh(@HeaderParam("RefreshToken") String refreshToken);

    @Path("/protected")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getProtected();
}
