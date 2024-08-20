package com.client.ws.rasmooplus.dto.wsraspay;

public record PaymentDto(CreditCardDto creditCard, String customerId, String orderId) {
}
