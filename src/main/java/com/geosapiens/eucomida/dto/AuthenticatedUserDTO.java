package com.geosapiens.eucomida.dto;

import java.util.Map;

public record AuthenticatedUserDTO(
    String authType,
    String sub,
    String email,
    String name,
    Map<String, Object> claims
) {}
