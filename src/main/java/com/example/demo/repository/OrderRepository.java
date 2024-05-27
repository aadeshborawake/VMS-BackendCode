package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Order;
import com.example.demo.entity.Vendor;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByOrderDate(Date date);
    List<Order> findByOrderDateBetween(Date startDate, Date endDate);
    List<Order> findByUserId(Long userId);
	List<Order> findByVendorId(Long vendorId);
	List<Order> findByVendor(Vendor vendor);
    
    // Custom query method to find monthly orders by user ID and year-month
	List<Order> findByUserIdAndOrderDateBetween(Long userId, Date startDate, Date endDate);
	
	 @Query("SELECT DISTINCT YEAR(o.orderDate) FROM Order o")
	    List<Integer> getAllYears();

}
