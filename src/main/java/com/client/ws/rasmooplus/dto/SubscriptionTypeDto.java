package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record SubscriptionTypeDto(

        Long id,

        @NotBlank(message = "Campo não pode ser nulo ou vazio")
        @Size(min = 5, max = 30,  message = "Campo deve ter tamanho entre 5 e 30")
        String name,

        @Max(value = 12, message = "O campo não pode ser maior que 12")
        Long accessMonth,

        @NotNull(message = "Campo não pode ser nulo")
        BigDecimal price,

        @NotBlank(message = "Campo não pode ser nulo ou vazio")
        @Size(min = 5, max = 15, message = "Campo deve ter tamanho entre 5 e 15") String productKey) {
}
