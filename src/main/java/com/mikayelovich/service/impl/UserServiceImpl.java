package com.mikayelovich.service.impl;

import com.mikayelovich.dao.UserDao;
import com.mikayelovich.model.UserEntity;
import com.mikayelovich.service.UserService;
import com.mikayelovich.service.impl.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserEntity getById(Long id) {
        return userDao.getById(id)
                .orElseThrow(() ->
                        new NotFoundException("User with id " + id + " not found."));
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
    public List<UserEntity> getAllUsers() {
        return userDao.findAll();
    }
}
