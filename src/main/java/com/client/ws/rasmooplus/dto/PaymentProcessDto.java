package com.client.ws.rasmooplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentProcessDto(

        @NotBlank(message = "deve ser informado") String productKey,

        BigDecimal discount,

        @NotNull(message = "deve ser informado") @JsonProperty("userPaymentInfo") PaymentInfoDto paymentInfoDto

) {
}
