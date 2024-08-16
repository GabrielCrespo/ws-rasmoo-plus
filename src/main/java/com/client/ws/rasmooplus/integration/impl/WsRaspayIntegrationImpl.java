package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    @Value("${webservices.raspay.host}")
    private String raspayHost;

    @Value("${webservices.raspay.v1.customer}")
    private String customerUrl;

    @Value("${webservices.raspay.v1.order}")
    private String orderUrl;

    @Value("${webservices.raspay.v1.payment}")
    private String paymentUrl;

    private static final Logger LOGGER = LoggerFactory.getLogger(WsRaspayIntegrationImpl.class);

    private final RestTemplate restTemplate;

    public WsRaspayIntegrationImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            String credentials = "rasmoo:r@sm00";
            String base64 = new String(Base64.getEncoder().encode(credentials.getBytes()));
            httpHeaders.add("Authorization", "Basic " + base64);
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, httpHeaders);

            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange(raspayHost + customerUrl,
                            HttpMethod.POST,
                            request,
                            CustomerDto.class);

            return response.getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            String credentials = "rasmoo:r@sm00";
            String base64 = new String(Base64.getEncoder().encode(credentials.getBytes()));
            httpHeaders.add("Authorization", "Basic " + base64);
            HttpEntity<OrderDto> request = new HttpEntity<>(dto, httpHeaders);

            ResponseEntity<OrderDto> response =
                    restTemplate.exchange(raspayHost + orderUrl,
                            HttpMethod.POST,
                            request,
                            OrderDto.class);

            return response.getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

    }

    @Override
    public Boolean processPayment(PaymentDto dto) {

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            String credentials = "rasmoo:r@sm00";
            String base64 = new String(Base64.getEncoder().encode(credentials.getBytes()));
            httpHeaders.add("Authorization", "Basic " + base64);
            HttpEntity<PaymentDto> request = new HttpEntity<>(dto, httpHeaders);

            ResponseEntity<Boolean> response =
                    restTemplate.exchange(raspayHost + customerUrl,
                            HttpMethod.POST,
                            request,
                            Boolean.class);

            return response.getBody();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

}
