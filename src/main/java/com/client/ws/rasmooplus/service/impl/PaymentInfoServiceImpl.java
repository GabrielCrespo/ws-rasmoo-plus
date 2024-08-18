package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.model.UserPaymentInfo;
import com.client.ws.rasmooplus.repository.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;

import java.util.Objects;

public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final UserRepository userRepository;

    private final UserPaymentInfoRepository userPaymentInfoRepository;

    public PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
    }

    @Override
    public Boolean process(PaymentProcessDto dto) {

        var user = userRepository.findById(dto.paymentInfoDto().userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pôde ser processado pois usuário já possui assinatura");
        }

        UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.paymentInfoDto(), user);
        userPaymentInfoRepository.save(userPaymentInfo);

        return false;
    }
}
