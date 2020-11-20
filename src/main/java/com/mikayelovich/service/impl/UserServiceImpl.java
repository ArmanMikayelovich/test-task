package com.mikayelovich.service.impl;

import com.mikayelovich.dao.UserDao;
import com.mikayelovich.model.UserEntity;
import com.mikayelovich.model.dto.UserDto;
import com.mikayelovich.service.Mapper;
import com.mikayelovich.service.UserService;
import com.mikayelovich.service.impl.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service

@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final Mapper mapper;

    public UserServiceImpl(UserDao userDao, Mapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    @Override
    public UserEntity getEntityById(Long id) {
        return userDao.getById(id)
                .orElseThrow(() ->
                        new NotFoundException("User with id " + id + " not found."));
    }

    @Override
    public UserDto getById(Long id) {
        return mapper.map(getEntityById(id), new UserDto());
    }

    @Override
    @Transactional
    public void save(UserEntity userEntity) {
        userDao.save(userEntity);
    }

    @Override
    @Transactional
    public void update(UserEntity userEntity) {
        userDao.update(userEntity);
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userDao.delete(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userDao
                .findAll()
                .stream().map(entity -> mapper.map(entity, new UserDto()))
                .collect(Collectors.toList());
    }

}
