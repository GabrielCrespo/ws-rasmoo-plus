package com.client.ws.rasmooplus.dto;

import java.math.BigDecimal;

public record SubscriptionTypeDto(Long id, String name, Long accesMonth, BigDecimal price, String productKey) {
}
