package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.model.SubscriptionType;

public class SubscriptionTypeMapper {

    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDto dto) {

        return SubscriptionType.builder()
                .name(dto.name())
                .accessMonth(dto.accessMonth())
                .price(dto.price())
                .productKey(dto.productKey())
                .build();

    }

}
