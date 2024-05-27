package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entity.Product;
import com.example.demo.entity.Vendor;
import com.example.demo.service.ProductService;
import com.example.demo.service.VendorService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("http://localhost:3000")
public class ProductController {


	@Autowired
    private ProductService productService;
	
    @GetMapping("/list")
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }
    
    
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @RequestHeader("user-role") String userRole) {
        if (!userRole.equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // Check if product description is null or empty, if so, set a default value
        if (product.getProductDescription() == null || product.getProductDescription().isEmpty()) {
            product.setProductDescription("Default Description");
        }
        
        Product newProduct = productService.addProduct(product);
        return ResponseEntity.ok(newProduct);
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId, @RequestHeader("user-role") String userRole) {
        if (!userRole.equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
    
    
    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct, @RequestHeader("user-role") String userRole) {
        if (!userRole.equals("admin")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        Product existingProduct = productService.getProductById(productId);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Update the existing product with the new details
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setProductDescription(updatedProduct.getProductDescription());
        existingProduct.setProductPrice(updatedProduct.getProductPrice());
        
        // Save the updated product
        Product savedProduct = productService.updateProduct(existingProduct);
        return ResponseEntity.ok(savedProduct);
    }
    
}







































//import java.math.BigDecimal;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.entity.Product;
//import com.example.demo.service.ProductService;
//
//@RestController
//@RequestMapping("/api/products")
//@CrossOrigin("*")
//public class ProductController {
//
//  @Autowired
//  private ProductService productService;
//
//  @GetMapping("/list")
//  public ResponseEntity<List<Product>> listProducts() {
//      List<Product> products = productService.getAllProducts();
//      return ResponseEntity.ok(products);
//  }
//  
//
//  @PostMapping("/add")
//  public ResponseEntity<Product> addProduct(@RequestBody Product product, @RequestHeader("user-role") String userRole) {
//      if (!userRole.equals("admin")) {
//          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//      }
//
//      if (product == null || 
//          product.getProductName() == null || 
//          product.getProductName().isEmpty() || 
//          product.getProductPrice() == null || 
//          product.getProductPrice().compareTo(BigDecimal.ZERO) <= 0) {
//          return ResponseEntity.badRequest().build();
//      }
//
//      if (product.getProductDescription() == null || product.getProductDescription().isEmpty()) {
//          product.setProductDescription("Default Description");
//      }
//
//      Product newProduct = productService.addProduct(product);
//      return ResponseEntity.ok(newProduct);
//  }
//
//  @DeleteMapping("/delete/{productId}")
//  public ResponseEntity<Void> deleteProduct(@PathVariable Long productId, @RequestHeader("user-role") String userRole) {
//      if (!userRole.equals("admin")) {
//          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//      }
//
//      Product existingProduct = productService.getProductById(productId);
//      if (existingProduct == null) {
//          return ResponseEntity.notFound().build();
//      }
//
//      productService.deleteProduct(productId);
//      return ResponseEntity.noContent().build();
//  }
//
//  @PutMapping("/update/{productId}")
//  public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct, @RequestHeader("user-role") String userRole) {
//      if (!userRole.equals("admin")) {
//          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//      }
//
//      Product existingProduct = productService.getProductById(productId);
//      if (existingProduct == null) {
//          return ResponseEntity.notFound().build();
//      }
//
//      if (updatedProduct == null || 
//          updatedProduct.getProductName() == null || 
//          updatedProduct.getProductName().isEmpty() || 
//          updatedProduct.getProductPrice() == null || 
//          updatedProduct.getProductPrice().compareTo(BigDecimal.ZERO) <= 0) {
//          return ResponseEntity.badRequest().build();
//      }
//
//      existingProduct.setProductName(updatedProduct.getProductName());
//      existingProduct.setProductDescription(updatedProduct.getProductDescription());
//      existingProduct.setProductPrice(updatedProduct.getProductPrice());
//
//      Product savedProduct = productService.updateProduct(existingProduct);
//      return ResponseEntity.ok(savedProduct);
//  }
//}
