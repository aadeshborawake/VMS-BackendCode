package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {
	Vendor findByUserId(Long userId);

	Vendor findByVendorName(String vendorName);
}
