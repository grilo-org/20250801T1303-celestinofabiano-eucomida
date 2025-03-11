package com.geosapiens.eucomida.service.impl;

import com.geosapiens.eucomida.dto.UserRequestDTO;
import com.geosapiens.eucomida.dto.UserResponseDTO;
import com.geosapiens.eucomida.service.AuthenticationService;
import com.geosapiens.eucomida.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<String> getTokenFromAuthentication(Authentication authentication) {
        return switch (authentication) {
            case JwtAuthenticationToken jwtAuth -> Optional.of(jwtAuth.getToken().getTokenValue());
            case OAuth2AuthenticationToken oauth when oauth.getPrincipal() instanceof OidcUser oidcUser ->
                    Optional.of(oidcUser.getIdToken().getTokenValue());
            default -> Optional.empty();
        };
    }

    @Override
    public Optional<UserResponseDTO> getAuthenticatedUser(Authentication authentication) {
        return Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .flatMap(this::processAuthenticatedUser);
    }

    private Optional<UserResponseDTO> processAuthenticatedUser(Object principal) {
        return switch (principal) {
            case OidcUser oidcUser -> createOrGetUser(oidcUser.getFullName(), oidcUser.getEmail());
            case Jwt jwt ->
                    createOrGetUser(jwt.getClaimAsString("name"), jwt.getClaimAsString("email"));
            default -> Optional.empty();
        };
    }

    private Optional<UserResponseDTO> createOrGetUser(String name, String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(userService.getOrCreateUser(
                UserRequestDTO.builder()
                        .name(name != null ? name : email)
                        .email(email)
                        .build()
        ));
    }
}
