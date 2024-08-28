package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.model.UserType;
import com.client.ws.rasmooplus.repository.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserTypeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeService;

    @Test
    void givenfindAllThereAreDataInDatabaseThenReturnAllData() {

        List<UserType> userTypeList = Arrays.asList(
                new UserType(1L, "Professor", "Professor da plataforma"),
                new UserType(1L, "Administrador", "Administrador da plataforma")
        );

        Mockito.when(userTypeRepository.findAll()).thenReturn(userTypeList);

        var result = userTypeService.findAll();

        Assertions.assertThat(result).isNotEmpty().hasSize(2);

    }

}
