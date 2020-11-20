package com.mikayelovich.dao;

import com.mikayelovich.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<UserEntity> getById(Long id);

    void save(UserEntity userEntity);

    void update(UserEntity userEntity);

    void delete(Long userId);

    List<UserEntity> findAll();

}
