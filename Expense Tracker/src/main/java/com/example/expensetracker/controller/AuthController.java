package com.example.expensetracker.controller;

import com.example.expensetracker.dto.UserRequest;
import com.example.expensetracker.service.UserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/login", params = {"userName", "password"})
    public String loginUser(@RequestParam String userName, @RequestParam String password){
        return userDetailsService.loginUser(userName, password);
    }

    @PostMapping(value = "/register", params = {"userRequest"})
    public void registerUser(@Valid @RequestParam UserRequest userRequest){
        userDetailsService.registerUser(userRequest);
    }
}
