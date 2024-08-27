package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCredentialsDto(

        @Email(message = "email inválido") String email,

        @NotBlank(message = "atributo inválido") String password,

        String recoveryCode) {
}
