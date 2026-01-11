package com.workintech.FSWEB_s19_Challenge.controller;

import com.workintech.FSWEB_s19_Challenge.entity.User;
import com.workintech.FSWEB_s19_Challenge.dto.LoginRequest;
import com.workintech.FSWEB_s19_Challenge.dto.RegisterRequest;
import com.workintech.FSWEB_s19_Challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

}
