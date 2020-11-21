package com.mikayelovich.model.dto;

import lombok.Data;

@Data
public class AuthenticationDto {
    private String username;

    private String password;
}
