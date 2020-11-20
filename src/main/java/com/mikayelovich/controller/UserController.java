package com.mikayelovich.controller;

import com.mikayelovich.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllUsers(@RequestParam(name = "userId", required = false) Long id) {
        if (id != null) {
            return userService.getById(id);
        }
        return userService.getAllUsers();
    }
}
