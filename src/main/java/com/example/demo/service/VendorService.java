package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.VendorDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.exception.VendorNotFoundException;
import com.example.demo.repository.VendorRepository;

@Service
public class VendorService {

//	@Autowired
//    private VendorRepository vendorRepository;
	
	private final VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }
	
    public Vendor registerVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }
    
    public Vendor getVendorById(Long vendorId) {
        // Check if the vendorId is not null
        if (vendorId == null) {
            throw new IllegalArgumentException("Vendor ID must not be null.");
        }
        
        // Retrieve the vendor from the repository
        Optional<Vendor> vendorOptional = vendorRepository.findById(vendorId);
        
        // Check if the vendor exists
        if (vendorOptional.isPresent()) {
            return vendorOptional.get();
        } else {
            throw new VendorNotFoundException("Vendor with ID " + vendorId + " not found.");
        }
    }   
    
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

/////
    public VendorDTO getVendorProfileByUserId(Long userId) {
        // Retrieve the vendor entity by user ID from the repository
        Vendor vendor = vendorRepository.findByUserId(userId);
        if (vendor == null) {
            return null; // If vendor is not found, return null
        }
        // Map vendor entity to vendor DTO
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setVendorName(vendor.getVendorName());
        vendorDTO.setContactPersonFirstName(vendor.getContactPersonFirstName());
        vendorDTO.setContactPersonLastName(vendor.getContactPersonLastName());
        vendorDTO.setContactPersonEmail(vendor.getContactPersonEmail());
        vendorDTO.setContactPersonNumber(vendor.getContactPersonNumber());
        return vendorDTO;
    }
}
