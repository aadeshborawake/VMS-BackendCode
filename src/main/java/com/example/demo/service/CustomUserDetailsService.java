package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.VendorUser;
import com.example.demo.repository.VendorUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 @Autowired
	    private VendorUserRepository vendorUserRepository;

	 @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        VendorUser vendorUser = vendorUserRepository.findByUsername(username);

	        if (vendorUser == null) {
	            throw new UsernameNotFoundException("User not found");
	        }

	        // Check if the user is active
	        if (!vendorUser.isActive()) {
	            throw new UsernameNotFoundException("User account is deactivated");
	        }

	        return new org.springframework.security.core.userdetails.User(
	                vendorUser.getUsername(),
	                vendorUser.getPassword(),
	                new ArrayList<>());
	    }	
}
