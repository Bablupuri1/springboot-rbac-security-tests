package com.example.demo.SpringSecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Model.Users;
import com.example.demo.Repo.UserRepository;

import jakarta.annotation.PostConstruct;

@Configuration
public class DefaultUsersConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DefaultUsersConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createDefaultUsers() {
        if (userRepository.findByUsername("admin") == null) {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user") == null) {
            Users user = new Users();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            userRepository.save(user);
        }

        System.out.println("Default users created: admin and user");
    }
}
