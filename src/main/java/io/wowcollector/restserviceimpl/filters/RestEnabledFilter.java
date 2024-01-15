package io.wowcollector.restserviceimpl.filters;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import java.util.Optional;

public class RestEnabledFilter {

    @Inject
    ResourceInfo resourceInfo;

    @ConfigProperty(name = "IS_REST_APP", defaultValue = "false")
    private boolean myIsRestApp;

    @ServerRequestFilter()
    public Optional<Response> preMatchingFilter(ContainerRequestContext requestContext) {
        if (!myIsRestApp) {
            return Optional.of(Response.status(Response.Status.NOT_FOUND)
                                       .build());
        }
        return Optional.empty();
    }
}
