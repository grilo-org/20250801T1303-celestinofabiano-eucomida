package com.geosapiens.eucomida.controller;

import com.geosapiens.eucomida.dto.AuthenticatedUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/me")
    public AuthenticatedUserDTO getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Usuário não autenticado!");
        }

        return switch (authentication.getPrincipal()) {
            case OidcUser oidcUser -> new AuthenticatedUserDTO(
                    "oauth2_login",
                    oidcUser.getSubject(),
                    oidcUser.getEmail(),
                    oidcUser.getFullName(),
                    oidcUser.getClaims()
            );
            case Jwt jwt -> new AuthenticatedUserDTO(
                    "jwt_token",
                    jwt.getSubject(),
                    jwt.getClaimAsString("email"),
                    jwt.getClaimAsString("name"),
                    jwt.getClaims()
            );
            default -> throw new RuntimeException("Tipo de autenticação desconhecido!");
        };
    }
}
