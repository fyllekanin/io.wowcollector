package io.wowcollector.restservice.monitoring;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;


@Path("/api/v1/monitoring")
@Tags(value = @Tag(name = "Monitoring"))
public interface RestMonitoringService {

    @GET
    @Path("/readiness")
    @APIResponse(responseCode = "200")
    Response getReadiness();

}
