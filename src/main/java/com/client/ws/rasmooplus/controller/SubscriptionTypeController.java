package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subscription-type")
public class SubscriptionTypeController {

    private final SubscriptionTypeService subscriptionTypeService;

    public SubscriptionTypeController(SubscriptionTypeService subscriptionTypeService) {
        this.subscriptionTypeService = subscriptionTypeService;
    }

    @Cacheable(value = "subscriptionType")
    @GetMapping
    public ResponseEntity<List<SubscriptionType>> findAll() {
        return ResponseEntity.ok(subscriptionTypeService.findAll());
    }

    @Cacheable(value = "subscriptionType", key = "#id")
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionType> findById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionTypeService.findById(id));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @PostMapping
    public ResponseEntity<SubscriptionType> create(@RequestBody @Valid SubscriptionTypeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.create(dto));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionType> update(@PathVariable Long id, @RequestBody @Valid SubscriptionTypeDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypeService.update(id, dto));
    }

    @CacheEvict(value = "subscriptionType", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subscriptionTypeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
