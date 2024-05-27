package com.example.demo.entity;
	
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
	
@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
			    
	@NotBlank(message = "Product name is required")
	@Column(name = "product_name", nullable = false)
		private String productName;
			
	@NotBlank(message = "Product category is required")
	@Column(name = "product_category", nullable = false)
		private String productCategory;
			
	@NotNull(message = "Product price is required")
	@Column(name = "product_price", nullable = false)
	private BigDecimal productPrice;

	//@NotNull(message = "Product quantity is required")
	@Column(name = "product_quantity")
		private Integer productQuantity;
			
	@NotBlank(message = "Product description is required")
	@Column(name = "product_description", nullable = false)
		private String productDescription;
			
	@NotBlank(message = "Product image URL is required")
	@Column(name = "product_image_url", nullable = false)
		private String productImageUrl;
	
	@OneToMany(mappedBy = "product")
	@JsonIgnore // Prevent Jackson from serializing the orders field
	private List<Order> orders;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id") // Assuming vendor_id is the foreign key column in the products table
    private Vendor vendor;

	 
	public Product() {
	super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public Vendor getVendor() {
	        return vendor;
	    }
	
	public void setVendor(Vendor vendor) {
	    this.vendor = vendor;
	}
	
}
