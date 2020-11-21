package com.mikayelovich.service.impl;

import com.mikayelovich.dao.UserDao;
import com.mikayelovich.model.UserEntity;
import com.mikayelovich.model.dto.AuthenticationDto;
import com.mikayelovich.service.AuthService;
import com.mikayelovich.service.Mapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;

    public AuthServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, Mapper mapper) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public void checkCredentials(AuthenticationDto dto) {
        final Optional<UserEntity> optionalUserEntity = userDao.getByUsername(dto.getUsername());
        final String password = dto.getPassword();

        final boolean isValidCredentials = optionalUserEntity.isPresent()
                && isPasswordsMatches(password, optionalUserEntity.get().getPassword());

        if (!isValidCredentials) {
            throw new BadCredentialsException("username/password are incorrect");
        }
    }

    @Override
    @Transactional
    public void register(AuthenticationDto dto) {
        final String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);
        final UserEntity userEntity = mapper.map(dto, new UserEntity());
        userDao.save(userEntity);
    }

    private boolean isPasswordsMatches(String password, String hashedPassword) {
        return passwordEncoder.matches(password, hashedPassword);
    }

}
