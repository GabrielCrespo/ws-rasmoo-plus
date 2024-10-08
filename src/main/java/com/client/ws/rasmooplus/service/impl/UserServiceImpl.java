package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.model.User;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.repository.UserTypeRepository;
import com.client.ws.rasmooplus.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    public UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {

        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;

    }

    @Override
    public User create(UserDto dto) {

        if (Objects.nonNull(dto.id())) {
            throw new BadRequestException("Id deve ser nulo");
        }

        var userType = userTypeRepository.findById(dto.userTypeId()).orElseThrow(() -> new NotFoundException("userTypeId não encontrado"));

        User user = UserMapper.fromDtoToEntity(dto, userType, null);

        return userRepository.save(user);
    }


}
