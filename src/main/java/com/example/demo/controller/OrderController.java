package com.example.demo.controller;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderRequestDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.entity.Vendor;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.VendorNotFoundException;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.service.VendorService;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("http://localhost:3000")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private VendorService vendorService;

	
    @GetMapping("/list")
    public ResponseEntity<List<Order>> listOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
 // For admin users
    @PostMapping("/add/admin")
    public ResponseEntity<?> addOrderForAdmin(@RequestBody OrderRequestDTO orderRequest) {
        try {
            // Retrieve product
            Product product = productService.getProductById(orderRequest.getProductId());

            // Retrieve vendor
            Vendor vendor = vendorService.getVendorById(orderRequest.getVendorId());

            // Retrieve user by ID (you need to implement this method)
            User user = userService.getUserById(orderRequest.getUserId());

            // Check if product, vendor, and user exist
            if (product == null || vendor == null || user == null) {
                return ResponseEntity.notFound().build();
            }

            // Calculate order amount
            BigDecimal orderAmount = product.getProductPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity()));

            // Create the Order object and set properties
            Order order = new Order();
            order.setProduct(product);
            order.setVendor(vendor);
            order.setUser(user); // Set the user retrieved from the request
            order.setOrderQuantity(orderRequest.getQuantity());
            order.setOrderAmount(orderAmount);
            // Set order date to current date
            order.setOrderDate(LocalDateTime.now());

            // Save the order to the database
            Order savedOrder = orderService.createOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add order: " + e.getMessage());
        }
    }

    // For non-admin users
    @PostMapping("/add/user")
    public ResponseEntity<?> addOrderForUser(@RequestBody OrderRequestDTO orderRequest) {
        try {
            // Validate order request
            if (orderRequest.getProductId() == null || orderRequest.getUserId() == null || orderRequest.getVendorId() == null) {
                return ResponseEntity.badRequest().body("Product ID, User ID, and Vendor ID are required.");
            }

            // Retrieve product
            Product product = productService.getProductById(orderRequest.getProductId());

            // Check if product exists
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            // Retrieve user
            User user = userService.getUserById(orderRequest.getUserId());
            // Check if user exists
            if (user == null) {
                return ResponseEntity.notFound().build();
            }

            // Retrieve vendor
            Vendor vendor = vendorService.getVendorById(orderRequest.getVendorId());
            // Check if vendor exists
            if (vendor == null) {
                return ResponseEntity.notFound().build();
            }

            // Create the Order object and associate product, user, and vendor with it
            Order order = new Order();
            order.setProduct(product);
            order.setUser(user);
            order.setVendor(vendor);
            order.setOrderQuantity(orderRequest.getQuantity());

            // Calculate and set order amount
            BigDecimal orderAmount = product.getProductPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity()));
            order.setOrderAmount(orderAmount);

            // Set order date
            order.setOrderDate(LocalDateTime.now());

            // Save the order to the database
            Order savedOrder = orderService.createOrder(order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add order: " + e.getMessage());
        }
    }
    
	@DeleteMapping("/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId, @RequestHeader("user-role") String userRole) {
        if (!userRole.equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
    
//Admin
    @GetMapping("/daily")
    public ResponseEntity<List<Order>> getDailyReport(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Order> orders = orderService.getOrdersByDate(date);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<Order>> getMonthlyReport(@RequestParam("year") int year, @RequestParam("month") int month) {
        List<Order> orders = orderService.getOrdersByMonth(year, month);
        return ResponseEntity.ok(orders);
    }

//User
    @GetMapping("/orderlist")
    public ResponseEntity<List<Order>> getOrdersForCurrentUser(@RequestHeader("user-id") Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId); // You'll need to implement this method
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/daily-report")
    public ResponseEntity<List<Order>> getDailyReportForCurrentUser(
            @RequestHeader("user-id") Long userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<Order> dailyOrders = orderService.getDailyOrdersByUserId(userId, date);
            return ResponseEntity.ok(dailyOrders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/monthly-report")
    public ResponseEntity<List<Order>> getMonthlyReportForCurrentUser(
            @RequestHeader("user-id") Long userId,
            @RequestParam(value = "year") Integer year,
            @RequestParam("month") String month) { // Change type to String
        try {
            // Parse the month value to extract the integer value
            int parsedMonth = Integer.parseInt(month.split("-")[1]); // Extracting month part from "yyyy-MM" format
            List<Order> monthlyOrders = orderService.getMonthlyOrdersByUserId(userId, year, parsedMonth);
            return ResponseEntity.ok(monthlyOrders);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    //Added on 25 April
    @GetMapping("/availableYearsAndMonths")
    public ResponseEntity<Map<String, List<Integer>>> getAvailableYearsAndMonths() {
        Map<String, List<Integer>> result = new HashMap<>();
        List<Integer> years = orderService.getAvailableYears();
        List<Integer> months = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12); // Assuming all months are available
        result.put("years", years);
        result.put("months", months);
        return ResponseEntity.ok(result);
    }

}
//@GetMapping("/my-report")
//public ResponseEntity<List<Order>> getReportsForCurrentUser(@RequestHeader("user-id") Long userId) {
//  try {
//      List<Order> orders = orderService.getOrdersByUserId(userId);
//      return ResponseEntity.ok(orders);
//  } catch (Exception e) {
//      e.printStackTrace();
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//  }
//} 
