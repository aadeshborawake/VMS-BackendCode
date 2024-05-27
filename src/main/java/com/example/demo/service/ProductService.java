package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.Vendor;
import com.example.demo.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
	@Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

	public Product getProductById(Long productId) {
		return productRepository.findById(productId).orElse(null);
	}
    
    public Product updateProduct(Product existingProduct) {
      
        if (existingProduct == null || existingProduct.getId() == null) {
            
            return null;
        }

        Product productFromDB = productRepository.findById(existingProduct.getId()).orElse(null);
        if (productFromDB == null) {
            
            return null;
        }

        productFromDB.setProductName(existingProduct.getProductName());
        productFromDB.setProductDescription(existingProduct.getProductDescription());
        productFromDB.setProductPrice(existingProduct.getProductPrice());
        
        return productRepository.save(productFromDB);
    }

}
