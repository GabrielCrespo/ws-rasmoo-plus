package com.client.ws.rasmooplus.dto.wsraspay;

import java.math.BigDecimal;

public record OrderDto(String id, String customerId, BigDecimal discount, String productAcronym) {
}
