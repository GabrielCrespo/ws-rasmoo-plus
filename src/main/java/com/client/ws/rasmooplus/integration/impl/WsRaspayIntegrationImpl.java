package com.client.ws.rasmooplus.integration.impl;

import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import com.client.ws.rasmooplus.integration.WsRaspayIntegration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    public static final String AUTHORIZATION = "Authorization";

    public static final String BASIC = "Basic ";

    @Value("${webservices.raspay.host}")
    private String raspayHost;

    @Value("${webservices.raspay.v1.customer}")
    private String customerUrl;

    @Value("${webservices.raspay.v1.order}")
    private String orderUrl;

    @Value("${webservices.raspay.v1.payment}")
    private String paymentUrl;

    @Value("${webservices.raspay.credentials.user}")
    private String user;

    @Value("${webservices.raspay.credentials.password}")
    private String password;

    private final RestTemplate restTemplate;

    public WsRaspayIntegrationImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {

        try {
            HttpHeaders httpHeaders = getHttpHeaders();
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, httpHeaders);

            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange(raspayHost + customerUrl,
                            HttpMethod.POST,
                            request,
                            CustomerDto.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {

        try {
            HttpHeaders httpHeaders = getHttpHeaders();
            HttpEntity<OrderDto> request = new HttpEntity<>(dto, httpHeaders);

            ResponseEntity<OrderDto> response =
                    restTemplate.exchange(raspayHost + orderUrl,
                            HttpMethod.POST,
                            request,
                            OrderDto.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode());
        }

    }

    @Override
    public Boolean processPayment(PaymentDto dto) {

        try {
            HttpHeaders httpHeaders = getHttpHeaders();
            HttpEntity<PaymentDto> request = new HttpEntity<>(dto, httpHeaders);

            ResponseEntity<Boolean> response =
                    restTemplate.exchange(raspayHost + paymentUrl,
                            HttpMethod.POST,
                            request,
                            Boolean.class);

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String credentials = String.format("%s:%s", user, password);
        String base64 = new String(Base64.getEncoder().encode(credentials.getBytes()));
        httpHeaders.add(AUTHORIZATION, BASIC + base64);
        return httpHeaders;
    }

}
