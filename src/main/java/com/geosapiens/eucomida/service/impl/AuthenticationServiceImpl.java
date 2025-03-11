package com.geosapiens.eucomida.service.impl;

import com.geosapiens.eucomida.dto.UserRequestDTO;
import com.geosapiens.eucomida.dto.UserResponseDTO;
import com.geosapiens.eucomida.service.AuthenticationService;
import com.geosapiens.eucomida.service.UserService;
import org.springframework.security.core.Authentication;
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
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return Optional.of(jwtAuth.getToken().getTokenValue());
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserResponseDTO> getAuthenticatedUser(Authentication authentication) {
        return Optional.ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .flatMap(this::processAuthentication);
    }

    private Optional<UserResponseDTO> processAuthentication(Object principal) {
        if (principal instanceof OidcUser oidcUser) {
            return createOrGetUser(oidcUser.getFullName(), oidcUser.getEmail());
        } else if (principal instanceof Jwt jwt) {
            return createOrGetUser(jwt.getClaimAsString("name"), jwt.getClaimAsString("email"));
        }
        return Optional.empty();
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
