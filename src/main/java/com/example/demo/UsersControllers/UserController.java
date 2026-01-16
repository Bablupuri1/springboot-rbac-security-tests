package com.example.demo.UsersControllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Users;
import com.example.demo.Repo.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    
    //this is automatic initialize by default this is  the spring boot feature 
    //and  following if only one  constractor then if multiple then we need to add Autowire 
    
    
    public UserController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // 1. Save USER role
    
    @PostMapping("/addUser")
    public String addUser(@RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encode password
        user.setRole("USER");
        userRepo.save(user);
        return "User saved successfully with role USER";
    }

    // 2. Save ADMIN role
    @PostMapping("/addAdmin")
    public String addAdmin(@RequestBody Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // encode password
        user.setRole("ADMIN");
        userRepo.save(user);
        return "Admin saved successfully with role ADMIN";
    }
}
