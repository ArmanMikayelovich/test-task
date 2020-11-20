package com.mikayelovich.service;

import com.mikayelovich.model.UserEntity;
import com.mikayelovich.model.dto.UserDto;

import java.util.List;

public interface UserService {

    UserEntity getEntityById(Long id);
    UserDto getById(Long id);

    void save(UserEntity userEntity);

    void update(UserEntity userEntity);

    void delete(Long userId);


    List<UserDto> getAllUsers();
}
