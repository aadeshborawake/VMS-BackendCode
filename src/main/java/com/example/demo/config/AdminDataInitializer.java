package com.example.demo.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Component
public class AdminDataInitializer implements CommandLineRunner{

	@Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user already exists
        if (!userRepository.existsByUsername("admin")) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin@123");
            //adminUser.setPassword(passwordEncoder.encode("adminpassword")); // Ensure to encode the password
            adminUser.setRoles(Collections.singleton("ROLE_ADMIN"));
            userRepository.save(adminUser);
        }
    }
}
