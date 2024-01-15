package io.wowcollector.restserviceimpl.filters.authentication;

import io.wowcollector.common.util.JwtUtils;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import java.lang.reflect.Method;
import java.util.Optional;

public class AuthenticationRequestFilter {

    @Inject
    ResourceInfo resourceInfo;

    @ConfigProperty(name = "JWT_SECRET")
    private Optional<String> myJwtSecret;

    @ServerRequestFilter()
    public Optional<Response> preMatchingFilter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(AuthPermission.class) && !isAccessTokenValid(requestContext)) {
            return Optional.of(Response.status(Response.Status.UNAUTHORIZED)
                                       .build());
        }
        return Optional.empty();
    }

    private boolean isAccessTokenValid(ContainerRequestContext containerRequestContext) {
        String header = containerRequestContext.getHeaderString(AuthPermission.AUTH_HEADER);
        String token = JwtUtils.getTokenFromAuthHeader(header);
        if (token == null) {
            return false;
        }

        return myJwtSecret.isPresent() && JwtUtils.getIdIfValid(token, myJwtSecret.get()) != null;
    }
}
