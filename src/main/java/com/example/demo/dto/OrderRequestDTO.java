package com.example.demo.dto;

import java.math.BigDecimal;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.Vendor;
import com.example.demo.service.ProductService;
import com.example.demo.service.VendorService;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDTO {

	@NotNull
	private Long productId;
	@NotNull
    private Long userId;
	@Min(1)
    private int quantity;
	@NotNull
    private Long vendorId;
	private Object vendorName; 

    // Constructors, getters, and setters


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public static OrderRequestDTO fromOrder(Order order) {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setUserId(order.getUser().getId());
        orderRequestDTO.setProductId(order.getProduct().getId());
        orderRequestDTO.setVendorId(order.getVendor().getId());
        // Set other properties as needed
        return orderRequestDTO;
    }
    
    public BigDecimal getProductPrice(ProductService productService) {
        Product product = productService.getProductById(this.productId);
        return product != null ? product.getProductPrice() : BigDecimal.ZERO; // Return BigDecimal.ZERO if product or price is not available
    } 
   
}

