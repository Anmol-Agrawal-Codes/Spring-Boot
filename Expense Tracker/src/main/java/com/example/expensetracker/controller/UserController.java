package com.example.expensetracker.controller;

import com.example.expensetracker.dto.UserRequest;
import com.example.expensetracker.dto.UserResponse;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void addUser(@RequestBody UserRequest user){
        userService.saveUser(user);
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.getUsers();
    }
}
