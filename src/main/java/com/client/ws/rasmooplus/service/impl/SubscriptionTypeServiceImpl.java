package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.controller.SubscriptionTypeController;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.SubscriptionTypeMapper;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
        return getSubscriptionType(id).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class)
                        .findById(id)).withSelfRel()
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class)
                        .update(id, null)).withRel("update")
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class)
                        .delete(id)).withRel("delete"));
    }

    @Override
    public SubscriptionType create(SubscriptionTypeDto dto) {

        if (Objects.nonNull(dto.id())) {
            throw new BadRequestException("O id deve ser nulo");
        }

        SubscriptionType subscriptionType = SubscriptionTypeMapper.fromDtoToEntity(dto);

        subscriptionType = subscriptionTypeRepository.save(subscriptionType);

        return subscriptionType;
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionTypeDto dto) {

        getSubscriptionType(id);


        SubscriptionType subscriptionType = SubscriptionTypeMapper.fromDtoToEntity(dto);
        subscriptionType.setId(id);
        subscriptionType = subscriptionTypeRepository.save(subscriptionType);

        return subscriptionType;

    }

    @Override
    public void delete(Long id) {
        getSubscriptionType(id);
        subscriptionTypeRepository.deleteById(id);
    }

    private SubscriptionType getSubscriptionType(Long id) {
        return subscriptionTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SubscriptionType não encontrado"));
    }
}
