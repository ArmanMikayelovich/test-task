package com.mikayelovich.service;

import com.mikayelovich.model.dto.AuthenticationDto;
import com.mikayelovich.model.enums.UserRole;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

public interface AuthService {
    void checkCredentials(AuthenticationDto dto);

    void register(AuthenticationDto dto);

    Set<GrantedAuthority> getAuthoritiesFromRoles(Set<UserRole> roles);

}
