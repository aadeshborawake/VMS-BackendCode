package com.example.demo.service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.demo.dto.VendorUserDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.entity.VendorUser;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.repository.VendorUserRepository;

import jakarta.transaction.Transactional;

@Service
public class VendorUserService {

	@Autowired
	private VendorUserRepository vendorUserRepository;

	@Autowired 
	private OrderService orderService;
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
    private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
//	public VendorUser createVendorUser(VendorUserDTO vendorUserDTO) {
//        VendorUser vendorUser = new VendorUser();
//        vendorUser.setUsername(vendorUserDTO.getUsername());
//        vendorUser.setPassword(vendorUserDTO.getPassword());
//        vendorUser.setRole(vendorUserDTO.getRole());
////        vendorUser.setActive(false); // Default to false
//     // Set the active status based on the input from the frontend
//        vendorUser.setActive(vendorUserDTO.isActive());
//        
//        
//        // Retrieve the Vendor from the database based on the vendorName
//        Vendor vendor = vendorRepository.findByVendorName(vendorUserDTO.getVendorName());
//        if (vendor != null) {
//            vendorUser.setVendor(vendor); // Set the Vendor for the VendorUser   
//            return vendorUserRepository.save(vendorUser); // Save the VendorUser
//        } else {
//            throw new RuntimeException("Vendor not found"); // Handle case where Vendor is not found
//        }
//    }
	
	public VendorUser createVendorUser(VendorUserDTO vendorUserDTO) {
	    VendorUser vendorUser = new VendorUser();
	    vendorUser.setUsername(vendorUserDTO.getUsername());
	    vendorUser.setPassword(vendorUserDTO.getPassword());
	    vendorUser.setRole(vendorUserDTO.getRole());
	    // Set the active status based on the input from the frontend
	    int activeStatus = vendorUserDTO.isActive() ? 1 : 0;
	    vendorUser.setActive(activeStatus);
	    
	    // Retrieve the Vendor from the database based on the vendorName
	    Vendor vendor = vendorRepository.findByVendorName(vendorUserDTO.getVendorName());
	    if (vendor != null) {
	        vendorUser.setVendor(vendor); // Set the Vendor for the VendorUser   
	        return vendorUserRepository.save(vendorUser); // Save the VendorUser
	    } else {
	        throw new RuntimeException("Vendor not found"); // Handle case where Vendor is not found
	    }
	}


//    public VendorUser authenticateVendorUser(VendorUserDTO vendorUserDTO) {
//        // Retrieve the vendor user from the database based on the provided username
//        VendorUser vendorUser = vendorUserRepository.findByUsername(vendorUserDTO.getUsername());
//
//        // Check if the user exists and the password matches
//        if (vendorUser != null && vendorUser.getPassword().equals(vendorUserDTO.getPassword())) {
//            return vendorUser;
//        }
//        return null; // Authentication failed
//    }
	
	public VendorUser authenticateVendorUser(VendorUserDTO vendorUserDTO) {
	    // Retrieve the vendor user from the database based on the provided username
	    VendorUser vendorUser = vendorUserRepository.findByUsername(vendorUserDTO.getUsername());

	    // Check if the user exists and the password matches
	    if (vendorUser != null && vendorUser.getPassword().equals(vendorUserDTO.getPassword())) {
	        // Check if the user is active
	        if (vendorUser.isActive()) {
	            return vendorUser; // User is active, allow login
	        } else {
	            throw new RuntimeException("User account is deactivated"); // User is deactivated, prevent login
	        }
	    }
	    return null; // Authentication failed
	}

    
    public VendorUser findByUsername(String username) {
        return vendorUserRepository.findByUsername(username);
    }
    
    //New Code
    
 // Add a method to fetch orders for a VendorUser based on their associated Vendor
    public List<Order> getOrdersForVendorUser(Long userId) {
        VendorUser vendorUser = vendorUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Vendor user not found"));

        if (vendorUser != null) {
            Vendor vendor = vendorUser.getVendor();
            if (vendor != null) {
                // Use OrderService to fetch orders by vendorId
                return orderService.getOrdersByVendorId(vendor.getId());
            } else {
                // Handle case where Vendor is not associated with the VendorUser
                return Collections.emptyList();
            }
        } else {
            // Handle case where VendorUser with specified userId is not found
            return Collections.emptyList();
        }
    }
    
 // VendorUserService.java
    public List<Order> getReportOrdersByVendorName(String vendorName) {
        // Implement the logic to fetch orders for the report based on the vendor name
        Vendor vendor = vendorRepository.findByVendorName(vendorName);
        if (vendor != null) {
            return orderRepository.findByVendor(vendor);
        } else {
            return Collections.emptyList();
        }
    }
    
    public List<VendorUser> getAllUsers() {
        return vendorUserRepository.findAll();
    }

    public List<VendorUser> getAllUsersForVendor(String vendorName) {
        return vendorUserRepository.findByVendorVendorName(vendorName);
    }
    
 //Added

    public List<VendorUser> getAllUsersForVendor(Long vendorId) {
        return vendorUserRepository.findByVendorId(vendorId);
    }
    
    public VendorUser activateVendorUser(Long userId) {
        VendorUser vendorUser = vendorUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Vendor user not found"));

        vendorUser.setActive(1); // Set active status to 1 (activated)
        return vendorUserRepository.save(vendorUser);
    }


    public VendorUser deactivateVendorUser(Long userId) {
        VendorUser vendorUser = vendorUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Vendor user not found"));

        vendorUser.setActive(0); // Set active status to 0 (deactivated)
        return vendorUserRepository.save(vendorUser);
    }

}
