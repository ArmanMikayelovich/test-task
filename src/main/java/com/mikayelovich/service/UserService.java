package com.mikayelovich.service;

import com.mikayelovich.model.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getById(Long id);

    void save(UserEntity userEntity);

    void update(UserEntity userEntity);

    void delete(Long userId);


    List<UserEntity> getAllUsers();
}
