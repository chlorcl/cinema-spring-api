package me.chlorcl.cinemaapi.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasRole(T(me.chlorcl.cinemaapi.model.user.Role).ROLE_ADMIN)")
public @interface AdminAuthorization {
}