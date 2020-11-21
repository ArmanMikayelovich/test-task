package com.mikayelovich.service;

import com.mikayelovich.model.dto.AuthenticationDto;

public interface AuthService {
    void checkCredentials(AuthenticationDto dto);

    void register(AuthenticationDto dto);
}
