package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
 
    
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public void saveOrder(Order order) {
        order.generateOrderNumber(); // Generate a unique order number before saving
        orderRepository.save(order);
    }
    
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
    
    public List<Order> getOrdersByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();
        logger.info("Fetching orders for date: {}", date);
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        logger.info("Found {} orders for date: {}", orders.size(), date);
        return orders;
    }

    public List<Order> getOrdersByMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        Date start = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date end = calendar.getTime();
        return orderRepository.findByOrderDateBetween(start, end);
    } 
    
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByVendorId(Long vendorId) {
        return orderRepository.findByVendorId(vendorId);
    }
    
  //NewCode
    public List<Order> getDailyOrdersByUserId(Long userId, LocalDate date) {
        LocalDateTime startOfDay = date.atTime(LocalTime.MIN);
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        Date startDate = convertToDate(startOfDay);
        Date endDate = convertToDate(endOfDay);
        return orderRepository.findByUserIdAndOrderDateBetween(userId, startDate, endDate);
    }

    public List<Order> getMonthlyOrdersByUserId(Long userId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);
        LocalDateTime startOfMonth = startDate.atTime(LocalTime.MIN);
        LocalDateTime endOfMonth = endDate.atTime(LocalTime.MAX);
        Date startDateOfMonth = convertToDate(startOfMonth);
        Date endDateOfMonth = convertToDate(endOfMonth);
        return orderRepository.findByUserIdAndOrderDateBetween(userId, startDateOfMonth, endDateOfMonth);
    }

    // Helper method to convert LocalDateTime to Date
    private Date convertToDate(LocalDateTime localDateTime) {
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    public List<Integer> getAvailableYears() {
        // Assuming you have a method to fetch all years from your database
        List<Integer> allYears = orderRepository.getAllYears(); // Assuming orderRepository is an instance of your repository/DAO class
        
        // If there are no orders, return an empty list
        if (allYears.isEmpty()) {
            return Collections.emptyList();
        }
        
        // Extract unique years from the list of all years
        Set<Integer> uniqueYears = new HashSet<>(allYears);
        
        // Convert the set back to a list and sort it
        List<Integer> sortedYears = new ArrayList<>(uniqueYears);
        Collections.sort(sortedYears);
        
        return sortedYears;
    }

}
