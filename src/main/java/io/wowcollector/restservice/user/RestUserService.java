package io.wowcollector.restservice.user;

import io.wowcollector.entityview.request.user.UserUpdateRequest;
import io.wowcollector.entityview.response.user.InitialUserResponse;
import io.wowcollector.entityview.response.user.UserHomePageResponse;
import io.wowcollector.restserviceimpl.filters.authentication.AuthPermission;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/api/v1/user")
@Tags(value = @Tag(name = "User"))
public interface RestUserService {

    @Path("/update")
    @PUT
    @AuthPermission
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateUser(@HeaderParam(AuthPermission.AUTH_HEADER) String authHeader, @RequestBody(description =
            "Fields to update", required = true, content = @Content(schema = @Schema(implementation =
            UserUpdateRequest.class))) String body);

    @Path("/me")
    @GET
    @AuthPermission
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation =
            UserHomePageResponse.class)))
    Response getUserHomePage(@HeaderParam(AuthPermission.AUTH_HEADER) String authHeader);

    @Path("/initial")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "No authorization header was provided or it was valid", content
            = @Content(schema = @Schema(implementation = InitialUserResponse.class)))
    @APIResponse(responseCode = "401", description = "Authorization header was provided but not valid")
    @Parameter(name = "Authorization",
            in = ParameterIn.HEADER)
    Response getInitial(@HeaderParam(AuthPermission.AUTH_HEADER) String authHeader);
}
