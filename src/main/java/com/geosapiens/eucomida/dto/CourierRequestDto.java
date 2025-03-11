package com.geosapiens.eucomida.dto;

import com.geosapiens.eucomida.constant.ValidationConstants;
import com.geosapiens.eucomida.entity.enums.VehicleType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;

public record CourierRequestDto(
        @NotNull(message = "O ID do usuário é obrigatório")
        UUID userId,

        @NotNull(message = "O tipo de veículo é obrigatório")
        VehicleType vehicleType,

        @Pattern(regexp = ValidationConstants.COURIER_PLATE_NUMBER_REGEX, message = ValidationConstants.COURIER_PLATE_NUMBER_MESSAGE)
        String plateNumber
) {

}
