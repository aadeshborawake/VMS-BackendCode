package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.VendorUserDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.entity.VendorUser;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.service.VendorUserService;

@RestController
@RequestMapping("/api/vendor-users")
@CrossOrigin("http://localhost:3000") 
public class VendorUserController {

	@Autowired
    private VendorUserService vendorUserService;
	
	@PostMapping("/vendorlogin")
    public ResponseEntity<VendorUser> vendorUserLogin(@RequestBody VendorUserDTO vendorUserDTO) {
        VendorUser authenticatedVendorUser = vendorUserService.authenticateVendorUser(vendorUserDTO);
        if (authenticatedVendorUser != null) {
        	authenticatedVendorUser.setPassword(null);
            return new ResponseEntity<>(authenticatedVendorUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

	@PostMapping("/create")
    public ResponseEntity<VendorUser> createVendorUser(@RequestBody VendorUserDTO vendorUserDTO) {
        VendorUser createdVendorUser = vendorUserService.createVendorUser(vendorUserDTO);
        return new ResponseEntity<>(createdVendorUser, HttpStatus.CREATED);
    }

    //NewCode
    
    @GetMapping("/vendororders/{userId}")
    public ResponseEntity<List<Order>> getOrdersForVendorUser(@PathVariable Long userId) {
        List<Order> orders = vendorUserService.getOrdersForVendorUser(userId);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
 // VendorUserController.java
    @GetMapping("/vendorreports/{vendorName}")
    public ResponseEntity<List<Order>> getReportOrdersForVendorUser(@PathVariable String vendorName) {
        List<Order> orders = vendorUserService.getReportOrdersByVendorName(vendorName);
        if (!orders.isEmpty()) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//Added
    
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorUser>> getAllUsersForVendor(@PathVariable Long vendorId) {
        List<VendorUser> users = vendorUserService.getAllUsersForVendor(vendorId);
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{userId}/activate")
    public ResponseEntity<VendorUser> activateVendorUser(@PathVariable Long userId) {
        VendorUser activatedUser = vendorUserService.activateVendorUser(userId);
        return new ResponseEntity<>(activatedUser, HttpStatus.OK);
    }

    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<VendorUser> deactivateVendorUser(@PathVariable Long userId) {
        VendorUser deactivatedUser = vendorUserService.deactivateVendorUser(userId);
        return new ResponseEntity<>(deactivatedUser, HttpStatus.OK);
    }
    
    
}

