package com.geosapiens.eucomida.service.impl;

import com.geosapiens.eucomida.dto.UserRequestDTO;
import com.geosapiens.eucomida.dto.UserResponseDTO;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.repository.UserRepository;
import com.geosapiens.eucomida.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserResponseDTO> findById(UUID userId) {
        return userRepository.findById(userId).map(this::convertToDTO);
    }

    @Override
    public Optional<UserResponseDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::convertToDTO);
    }

    @Override
    public UserResponseDTO getOrCreateUser(UserRequestDTO userRequest) {
        return userRepository.findByEmail(userRequest.email())
                .map(this::convertToDTO)
                .orElseGet(() -> registerNewUser(userRequest));
    }

    @Override
    public UserResponseDTO registerNewUser(UserRequestDTO userRequest) {
        User newUser = new User();
        newUser.setName(userRequest.name());
        newUser.setEmail(userRequest.email());

        User savedUser = userRepository.save(newUser);
        return convertToDTO(savedUser);
    }

    private UserResponseDTO convertToDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
