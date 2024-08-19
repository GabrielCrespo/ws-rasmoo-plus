package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.PaymentInfoDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;

public class CreditCardMapper {

    public static CreditCardDto build(PaymentInfoDto paymentInfoDto, String documentNumber) {
        return new CreditCardDto(
                Long.parseLong(paymentInfoDto.cardSecurityCode()),
                documentNumber,
                paymentInfoDto.installments(),
                paymentInfoDto.cardNumber(),
                paymentInfoDto.cardExpirationMonth(),
                paymentInfoDto.cardExpirationYear()
        );
    }

}
