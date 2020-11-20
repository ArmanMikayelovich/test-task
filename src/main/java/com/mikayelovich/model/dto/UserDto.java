package com.mikayelovich.model.dto;

import com.mikayelovich.model.enums.UserRole;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;

    private String name;

    private Set<UserRole> roles;

}
