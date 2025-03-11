package com.geosapiens.eucomida.dto;

import lombok.Builder;

@Builder
public record UserRequestDTO(String name, String email) {}
