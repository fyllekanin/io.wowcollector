package io.wowcollector.restserviceimpl.filters.authentication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AuthPermission {
    static final String AUTH_HEADER = "Authorization";
}
