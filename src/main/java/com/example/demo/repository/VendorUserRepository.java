package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.VendorUser;

@Repository
public interface VendorUserRepository extends JpaRepository<VendorUser, Long> {
	VendorUser findByUsername(String username);
	List<VendorUser> findByVendorVendorName(String vendorName);
    Optional<VendorUser> findByIdAndVendorVendorName(Long userId, String vendorName);

    List<VendorUser> findByVendorId(Long vendorId);
    
}
