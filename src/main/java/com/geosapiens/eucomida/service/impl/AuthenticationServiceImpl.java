package com.geosapiens.eucomida.service.impl;

import static com.geosapiens.eucomida.util.AuthenticationUtils.CLAIM_EMAIL;
import static com.geosapiens.eucomida.util.AuthenticationUtils.CLAIM_NAME;

import com.geosapiens.eucomida.dto.UserRequestDto;
import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import com.geosapiens.eucomida.exception.AuthenticatedUserNotFoundException;
import com.geosapiens.eucomida.exception.UserNotFoundInDatabaseException;
import com.geosapiens.eucomida.service.AuthenticationService;
import com.geosapiens.eucomida.service.UserService;
import com.geosapiens.eucomida.util.AuthenticationUtils;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    public AuthenticationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserResponseDto> findCurrentUserDto() {
        return AuthenticationUtils.getAuthentication()
                .flatMap(this::processAuthenticatedUser);
    }

    @Override
    public User findCurrentUser() {
        String email = getCurrentUserEmail()
                .orElseThrow(AuthenticatedUserNotFoundException::new);

        return userService.findByEmail(email)
                .orElseThrow(UserNotFoundInDatabaseException::new);
    }

    @Override
    public Optional<String> getCurrentUserEmail() {
        return AuthenticationUtils.getClaim(CLAIM_EMAIL);
    }

    @Override
    public Optional<String> getCurrentToken() {
        return AuthenticationUtils.getToken();
    }

    private Optional<UserResponseDto> processAuthenticatedUser(Authentication authentication) {
        String name = AuthenticationUtils.getClaim(CLAIM_NAME).orElse(null);
        String email = AuthenticationUtils.getClaim(CLAIM_EMAIL).orElse(null);
        return createOrGetUser(name, email);
    }

    private Optional<UserResponseDto> createOrGetUser(String name, String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(userService.getOrCreate(
                UserRequestDto.builder()
                        .name(name != null ? name : email)
                        .email(email)
                        .build()
        ));
    }

}
