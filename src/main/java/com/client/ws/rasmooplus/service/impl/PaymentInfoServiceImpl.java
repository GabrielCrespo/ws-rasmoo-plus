package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.enums.UserTypeEnum;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.integration.MailIntegration;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CustomerMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.OrderMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.rasmooplus.model.UserCredentials;
import com.client.ws.rasmooplus.model.UserPaymentInfo;
import com.client.ws.rasmooplus.model.UserType;
import com.client.ws.rasmooplus.repository.UserCredentialsRepository;
import com.client.ws.rasmooplus.repository.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.repository.UserTypeRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentInfoServiceImpl.class);

    private final UserRepository userRepository;

    private final UserPaymentInfoRepository userPaymentInfoRepository;

    private final WsRaspayIntegration wsRaspayIntegration;

    private final MailIntegration mailIntegration;

    private final UserCredentialsRepository userCredentialsRepository;

    private final UserTypeRepository userTypeRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PaymentInfoServiceImpl(
            UserRepository userRepository,
            UserPaymentInfoRepository userPaymentInfoRepository,
            WsRaspayIntegration wsRaspayIntegration,
            MailIntegration mailIntegration,
            UserCredentialsRepository userCredentialsRepository,
            UserTypeRepository userTypeRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
        this.userCredentialsRepository = userCredentialsRepository;
        this.userTypeRepository = userTypeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public Boolean process(PaymentProcessDto dto) {

        var user = userRepository.findById(dto.paymentInfoDto().userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pôde ser processado pois usuário já possui assinatura");
        }

        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.id(), dto));

        CreditCardDto creditCardDto = CreditCardMapper.build(dto.paymentInfoDto(), user.getCpf());
        PaymentDto paymentDto = PaymentMapper.build(customerDto.id(), orderDto.id(), creditCardDto);

        Boolean successPayment = wsRaspayIntegration.processPayment(paymentDto);

        if (Boolean.TRUE.equals(successPayment)) {

            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.paymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

//            mailIntegration.send(user.getEmail(), String.format("Usuario: %s - Senha: alunorasmoo", user.getEmail()),
//                    "Acesso liberado");

            UserType userType = userTypeRepository.findById(UserTypeEnum.ALUNO.getId())
                    .orElseThrow(() -> new NotFoundException("UserType não encontrado"));

            String password = RandomStringUtils.randomAlphanumeric(10);
            String encryptedPassowrd  = bCryptPasswordEncoder.encode(password);

            UserCredentials userCredentials = new UserCredentials(null, user.getEmail(), encryptedPassowrd, userType);
            userCredentials = userCredentialsRepository.save(userCredentials);

            LOGGER.info("Usuário: {} - Senha: {}", userCredentials.getUsername(), password);

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
