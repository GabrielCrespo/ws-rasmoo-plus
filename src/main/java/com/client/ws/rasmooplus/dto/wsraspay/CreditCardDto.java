package com.client.ws.rasmooplus.dto.wsraspay;

public record CreditCardDto(Long cvv, String documentNumber, Long installments, String number, Long month, Long year) {
}
