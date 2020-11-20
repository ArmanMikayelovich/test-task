package com.mikayelovich.dao.impl;

import com.mikayelovich.dao.UserDao;
import com.mikayelovich.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<UserEntity> getById(Long id) {
        final UserEntity userEntity = currentSession().get(UserEntity.class, id);
        return Optional.ofNullable(userEntity);
    }


    @Override
    public void save(UserEntity userEntity) {
        currentSession().persist(userEntity);
    }

    @Override
    public void update(UserEntity userEntity) {
        currentSession().update(userEntity);
    }

    @Override
    public void delete(Long userId) {
        getById(userId).ifPresent(user -> currentSession().delete(user));
    }

    @Override
    @Transactional
    public List<UserEntity> findAll() {
        return currentSession().createQuery("select u from UserEntity u ", UserEntity.class).list();
    }

    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
}
