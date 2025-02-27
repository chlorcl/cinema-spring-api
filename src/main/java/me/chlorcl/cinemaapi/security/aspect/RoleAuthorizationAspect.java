package me.chlorcl.cinemaapi.security.aspect;

import me.chlorcl.cinemaapi.security.annotation.RoleAuthorization;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class RoleAuthorizationAspect {

    @Before("@annotation(roleAuthorization)")
    public void checkRoleAuthorization(RoleAuthorization roleAuthorization) {
        List<String> roles = Arrays.stream(roleAuthorization.roles())
                .map(Enum::name)
                .toList();
        List<String> userRoles = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        boolean hasRole = userRoles.stream().anyMatch(roles::contains);
        if (!hasRole) {
            throw new SecurityException("User does not have the required roles: " + roles);
        }
    }
}