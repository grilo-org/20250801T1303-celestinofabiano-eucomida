package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.dto.UserRequestDTO;
import com.geosapiens.eucomida.dto.UserResponseDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Optional<UserResponseDTO> findById(UUID userId);

    Optional<UserResponseDTO> findByEmail(String email);

    UserResponseDTO getOrCreateUser(UserRequestDTO userRequest);

    UserResponseDTO registerNewUser(UserRequestDTO userRequest);

}
