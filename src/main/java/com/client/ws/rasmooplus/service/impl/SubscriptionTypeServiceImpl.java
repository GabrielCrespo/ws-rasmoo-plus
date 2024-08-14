package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return subscriptionTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SubscriptionType não encontrado"));
    }

    @Override
    public SubscriptionType create(SubscriptionTypeDto dto) {

        SubscriptionType subscriptionType = SubscriptionType.builder()
                .id(dto.id())
                .name(dto.name())
                .accessMonth(dto.accesMonth())
                .price(dto.price())
                .productKey(dto.productKey())
                .build();

        subscriptionType = subscriptionTypeRepository.save(subscriptionType);

        return subscriptionType;
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionType subscriptionType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
