package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType findById(Long id) {
        return getSubscriptionType(id);
    }

    @Override
    public SubscriptionType create(SubscriptionTypeDto dto) {

        if (Objects.nonNull(dto.id())) {
            throw new BadRequestException("O id deve ser nulo");
        }

        SubscriptionType subscriptionType = SubscriptionType.builder()
                .name(dto.name())
                .accessMonth(dto.accesMonth())
                .price(dto.price())
                .productKey(dto.productKey())
                .build();

        subscriptionType = subscriptionTypeRepository.save(subscriptionType);

        return subscriptionType;
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionTypeDto dto) {

        getSubscriptionType(id);

        SubscriptionType subscriptionType = SubscriptionType.builder()
                .id(id)
                .name(dto.name())
                .accessMonth(dto.accesMonth())
                .price(dto.price())
                .productKey(dto.productKey())
                .build();

        subscriptionType = subscriptionTypeRepository.save(subscriptionType);

        return subscriptionType;

    }

    @Override
    public void delete(Long id) {

    }

    private SubscriptionType getSubscriptionType(Long id) {
        return subscriptionTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SubscriptionType não encontrado"));
    }
}
