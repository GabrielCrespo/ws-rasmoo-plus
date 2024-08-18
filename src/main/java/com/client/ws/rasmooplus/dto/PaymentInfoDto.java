package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentInfoDto(

        Long id,

        @Size(min = 16, max = 16, message = "deve conter 16 caracteres") String cardNumber,

        @Min(value = 1) @Max(value = 12) Long cardExpirationMonth,

        Long cardExpirationYear,

        @Size(min = 3, max = 3, message = "deve conter 3 caracteres") String cardSecurityCode,

        BigDecimal price,

        Long instalments,

        LocalDate dtPayment,

        @NotNull(message = "deve ser informado") Long userId) {
}
