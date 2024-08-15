package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.model.User;
import com.client.ws.rasmooplus.model.UserType;

import java.time.LocalDate;
import java.util.Objects;

public class UserMapper {

    public static User fromDtoToEntity(UserDto dto, UserType userType, SubscriptionType subscriptionType) {

        LocalDate dtSubscription = Objects.isNull(dto.dtSubscription()) ? LocalDate.now() : dto.dtSubscription();
        LocalDate dtExpiration = Objects.isNull(dto.dtExpiration()) ? LocalDate.now() : dto.dtExpiration();

        return User.builder()
                .id(dto.id())
                .name(dto.name())
                .email(dto.email())
                .cpf(dto.cpf())
                .phone(dto.phone())
                .dtSubscription(dtSubscription)
                .dtExpiration(dtExpiration)
                .userType(userType)
                .subscriptionType(subscriptionType)
                .build();

    }

}
