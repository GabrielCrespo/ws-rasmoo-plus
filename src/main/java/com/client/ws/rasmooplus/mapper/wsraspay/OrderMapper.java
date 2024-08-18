package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;

public class OrderMapper {

    public static OrderDto build(String customerId, PaymentProcessDto paymentProcessDto) {
        return new OrderDto(null, customerId, paymentProcessDto.discount(), paymentProcessDto.productKey());
    }

}
