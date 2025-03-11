package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.dto.UserRequestDTO;
import com.geosapiens.eucomida.dto.UserResponseDTO;
import java.util.Optional;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Optional<String> getTokenFromAuthentication(Authentication authentication);

    Optional<UserResponseDTO> getAuthenticatedUser(Authentication authentication);

}
