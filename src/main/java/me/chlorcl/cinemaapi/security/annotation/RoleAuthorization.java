package me.chlorcl.cinemaapi.security.annotation;

import me.chlorcl.cinemaapi.model.user.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleAuthorization {
    Role[] roles();
}