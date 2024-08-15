package com.client.ws.rasmooplus.dto.wsraspay;

public record OrderDto(String id, String customerId, Long discount, String productAcronym) {
}
