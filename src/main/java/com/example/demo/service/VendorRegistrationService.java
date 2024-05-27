package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.VendorRegistrationDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorRepository;

import jakarta.transaction.Transactional;

@Service
public class VendorRegistrationService {
	
	private final VendorRepository vendorRepository;
    private final UserRepository userRepository;

    public VendorRegistrationService(VendorRepository vendorRepository, UserRepository userRepository) {
        this.vendorRepository = vendorRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vendor registerVendor(VendorRegistrationDTO vendorDTO) {
        try {
            Vendor vendor = new Vendor();
            vendor.setVendorName(vendorDTO.getVendorName());
            vendor.setVendorAddress(vendorDTO.getVendorAddress());
            vendor.setContactPersonFirstName(vendorDTO.getContactPersonFirstName());
            vendor.setContactPersonLastName(vendorDTO.getContactPersonLastName());
            vendor.setContactPersonEmail(vendorDTO.getContactPersonEmail());
            vendor.setContactPersonNumber(vendorDTO.getContactPersonNumber());

            User user = new User();
            user.setUsername(vendorDTO.getUsername());
            user.setPassword(vendorDTO.getPassword());

            User savedUser = userRepository.save(user);
            vendor.setUser(savedUser);

            return vendorRepository.save(vendor);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw new RuntimeException("Failed to register vendor: " + e.getMessage());
        }
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }
	
}
