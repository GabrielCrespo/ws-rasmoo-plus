package com.client.ws.rasmooplus.dto.error;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserTypeDto(

        Long id,

        @NotBlank(message = "valor não pode ser nulo ou vazio")
        @Size(min = 6, message = "valor mínimo igual a 6 caracteres")
        String name,

        @NotBlank(message = "valor não pode ser nulo ou vazio")
        @Size(min = 10, message = "valor mínimo igual a 6 caracteres")
        String description) {
}
