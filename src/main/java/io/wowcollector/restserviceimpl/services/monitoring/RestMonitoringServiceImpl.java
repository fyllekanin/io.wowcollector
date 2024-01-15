package io.wowcollector.restserviceimpl.services.monitoring;

import io.wowcollector.restservice.monitoring.RestMonitoringService;
import jakarta.ws.rs.core.Response;

public class RestMonitoringServiceImpl implements RestMonitoringService {

    @Override
    public Response getReadiness() {
        return Response.status(Response.Status.OK)
                .build();
    }
}
