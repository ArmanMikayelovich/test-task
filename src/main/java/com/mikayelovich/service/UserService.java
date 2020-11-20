package com.mikayelovich.service;

import com.mikayelovich.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity getById(Long id);

    void save(UserEntity userEntity);

    void update(UserEntity userEntity);

    void delete(Long userId);


    List<UserEntity> getAllUsers();
}
