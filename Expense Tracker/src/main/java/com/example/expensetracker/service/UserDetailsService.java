package com.example.expensetracker.service;

import com.example.expensetracker.dto.UserRequest;
import com.example.expensetracker.entity.User;
import com.example.expensetracker.error.UserNotFoundException;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public void registerUser(UserRequest dto){
        String email = dto.email();
        String password = dto.password();

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUserName(dto.userName());
        user.setEmail(email);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public String loginUser(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null) throw new UserNotFoundException("User not found!");

        if (!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Email or password is no correct.");
        }

        return jwtService.generateToken(email);
    }
}
