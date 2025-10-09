package com.pedro.silva.springboot_playground.shared;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class KeycloakJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = extractRealmRoles(jwt);

        String principalClaimName = "preferred_username";

        return new JwtAuthenticationToken(jwt, authorities, jwt.getClaimAsString(principalClaimName));
    }

    private Collection<GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

        if (realmAccess == null || !realmAccess.containsKey("roles")) {
            return Set.of();
        }

        Collection<String> roles = (Collection<String>) realmAccess.get("roles");

        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .collect(Collectors.toSet());
    }
}
