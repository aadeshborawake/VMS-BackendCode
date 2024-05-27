package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {

	@Autowired
    private UserService userService;

    @PostMapping("/grant-role")
    public ResponseEntity<?> grantRoleToUser(@RequestParam("username") String username, @RequestParam("role") String role) {
       
        User user = userService.findByUsername(username);
        if (user != null) {
            user.getRoles().add(role);
            userService.save(user);
            return ResponseEntity.ok("Role " + role + " granted to user " + username);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
