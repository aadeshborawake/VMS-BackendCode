package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.VendorUserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.VendorUser;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorUserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private VendorUserRepository vendorUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User loginUser(String username, String password) {
	   
	    User user = userRepository.findByUsername(username);
	    	if (user != null && password.equals(user.getPassword())) {
	        return user; 
	    } else {
	        return null; 
	    }
	}
 
  public boolean isUsernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
	  
	
	public User findByUsername(String username) {
	    return userRepository.findByUsername(username);
	}
	
	
	public User save(User user) {
		 return userRepository.save(user);
	}
	
	public User getUserById(Long userId) {
	    if (userId == null) {
	        throw new IllegalArgumentException("User ID cannot be null");
    }
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
}

	public String changePassword(String username, String currentPassword, String newPassword) {
	    User user = userRepository.findByUsername(username);
	    if (user == null) {
	        return "Invalid username provided";
	    }
	
	    // Compare passwords without encoding
	    if (!currentPassword.equals(user.getPassword())) {
	        return "Incorrect current password";
	    }
	
	    // Update password
	    user.setPassword(newPassword);
	    userRepository.save(user);
	    
	    return "Password changed successfully";
		}
	
}

