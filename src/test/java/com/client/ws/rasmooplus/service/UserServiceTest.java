package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.model.User;
import com.client.ws.rasmooplus.model.UserType;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.repository.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void givenCreateWhenidIsNotNullAndUserIsFoundThenReturnUserCreated() {

        var userDto = new UserDto(
                null,
                "Teste",
                "teste@email.com",
                "556699999999",
                "12345678900",
                LocalDate.now(),
                LocalDate.now(),
                1L,
                null);


        var userType = new UserType(1L, "Professor", "Professor da plataforma");

        Mockito.when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        User user = new User();
        user.setName(userDto.name());
        user.setEmail(userDto.email());
        user.setCpf(userDto.cpf());
        user.setPhone(userDto.phone());
        user.setDtSubscription(userDto.dtSubscription());
        user.setDtExpiration(userDto.dtExpiration());
        user.setUserType(userType);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        User userCreated = userService.create(userDto);

        Assertions.assertEquals(user, userCreated);


    }

}