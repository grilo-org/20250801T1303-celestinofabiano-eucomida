package com.geosapiens.eucomida.service;

import com.geosapiens.eucomida.dto.UserResponseDto;
import com.geosapiens.eucomida.entity.User;
import java.util.Optional;

public interface AuthenticationService {

    Optional<UserResponseDto> findCurrentUserDto();

    User findCurrentUser();

    Optional<String> getCurrentUserEmail();

    Optional<String> getCurrentToken();

}
