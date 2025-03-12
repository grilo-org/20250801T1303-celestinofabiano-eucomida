package com.geosapiens.eucomida.util;

import static com.geosapiens.eucomida.constant.ErrorMessages.UTILITY_CLASS;

import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuthenticationUtils {

    public static final String CLAIM_NAME = "name";
    public static final String CLAIM_EMAIL = "email";

    private AuthenticationUtils() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

    public static Optional<String> getClaim(String claimKey) {
        return getAuthentication()
                .map(Authentication::getPrincipal)
                .flatMap(principal -> switch (principal) {
                    case OidcUser oidcUser -> CLAIM_EMAIL.equals(claimKey)
                            ? Optional.ofNullable(oidcUser.getEmail())
                            : Optional.ofNullable(oidcUser.getFullName());
                    case Jwt jwt -> Optional.ofNullable(jwt.getClaimAsString(claimKey));
                    default -> Optional.empty();
                });
    }

    public static Optional<String> getToken() {
        return getAuthentication().flatMap(authentication -> switch (authentication) {
            case JwtAuthenticationToken jwtAuth -> Optional.of(jwtAuth.getToken().getTokenValue());
            case OAuth2AuthenticationToken oauth when oauth.getPrincipal() instanceof OidcUser oidcUser ->
                    Optional.ofNullable(oidcUser.getIdToken())
                            .flatMap(idToken -> Optional.ofNullable(idToken.getTokenValue()));
            default -> Optional.empty();
        });
    }

}
