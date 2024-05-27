package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorRepository;

import jakarta.transaction.Transactional;

@Service
public class RegistrationService {
	
	private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    public RegistrationService(VendorRepository vendorRepository, UserRepository userRepository) {
        this.vendorRepository = vendorRepository;
        this.userRepository = userRepository;
       
    }

    @Transactional
    public Vendor registerVendor(Vendor vendor) {
        try {
            
            if (vendor == null) {
                throw new IllegalArgumentException("Vendor must not be null");
            }
           
            User user = vendor.getUser();
            if (user == null) {
                throw new IllegalArgumentException("User must not be null");
            }
            
            vendor.setUser(null);
           
            User savedUser = userRepository.save(user);
            vendor.setUser(savedUser);
            
            return vendorRepository.save(vendor);
        } catch (Exception e) {
            
            e.printStackTrace();
            throw new RuntimeException("Failed to register vendor: " + e.getMessage());
        }
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

}
