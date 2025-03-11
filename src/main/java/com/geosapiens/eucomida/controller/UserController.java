package com.geosapiens.eucomida.controller;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType.BEARER;

import com.geosapiens.eucomida.dto.UserResponseDTO;
import com.geosapiens.eucomida.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obter usuário autenticado",
            description = "Retorna os dados do usuário autenticado com base no token JWT ou OAuth2.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado retornado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Token inválido ou expirado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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
