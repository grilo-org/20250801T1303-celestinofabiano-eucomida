package com.geosapiens.eucomida.controller;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

import com.geosapiens.eucomida.dto.UserResponseDTO;
import com.geosapiens.eucomida.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.path}/v1/user")
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(Authentication authentication) {
        return authenticationService.getAuthenticatedUser(authentication)
                .map(user -> ResponseEntity.ok()
                        .headers(createAuthorizationHeader(authentication))
                        .body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private HttpHeaders createAuthorizationHeader(Authentication authentication) {
        HttpHeaders headers = new HttpHeaders();
        authenticationService.getTokenFromAuthentication(authentication)
                .ifPresent(token -> headers.set(HttpHeaders.AUTHORIZATION, token));
        return headers;
    }

}
