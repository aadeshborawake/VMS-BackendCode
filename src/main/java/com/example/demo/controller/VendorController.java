package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.VendorDTO;
import com.example.demo.dto.VendorRegistrationDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.service.RegistrationService;
import com.example.demo.service.UserService;
import com.example.demo.service.VendorService;


@RestController
@RequestMapping("/api/vendors")
@CrossOrigin("http://localhost:3000")
public class VendorController {
	
	@Autowired
    private RegistrationService registrationService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }
	
	@PostMapping("/register")
    public ResponseEntity<Vendor> registerVendor(@RequestBody VendorRegistrationDTO vendorDTO) {
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

        vendor.setUser(user);

        Vendor newVendor = registrationService.registerVendor(vendor);
        newVendor.getUser().setPassword(null); // Avoid exposing password in the response
        return ResponseEntity.ok(newVendor);
    }

	@GetMapping("/registered")
    public ResponseEntity<List<VendorRegistrationDTO>> getAllRegisteredVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        List<VendorRegistrationDTO> vendorDTOs = vendors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vendorDTOs);
    }

    // Helper method to convert Vendor entity to DTO
    private VendorRegistrationDTO convertToDTO(Vendor vendor) {
        VendorRegistrationDTO vendorDTO = new VendorRegistrationDTO();
        vendorDTO.setVendorName(vendor.getVendorName());
        vendorDTO.setVendorAddress(vendor.getVendorAddress());
        vendorDTO.setContactPersonFirstName(vendor.getContactPersonFirstName());
        vendorDTO.setContactPersonLastName(vendor.getContactPersonLastName());
        vendorDTO.setContactPersonEmail(vendor.getContactPersonEmail());
        vendorDTO.setContactPersonNumber(vendor.getContactPersonNumber());
        vendorDTO.setUsername(vendor.getUser().getUsername());
        // Set other necessary fields
        return vendorDTO;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }
 ////   
    @GetMapping("/profile/{userId}")
    public ResponseEntity<VendorDTO> getVendorProfile(@PathVariable Long userId) {
        VendorDTO vendorDTO = vendorService.getVendorProfileByUserId(userId);
        if (vendorDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vendorDTO);
    }
   
}

