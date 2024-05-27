package com.example.demo.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore // Break circular reference
    private Vendor vendor;
    
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();
  
	
	public User(Long id, String username, String password, Vendor vendor, Set<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.vendor = vendor;
		//this.roles = roles;
		roles.add("user");/*Changes because In the User class constructor, you are passing roles as a parameter.
		However, you should initialize the roles set within the constructor, rather than assigning it from 
		the parameter*/
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String>  roles) {
		this.roles = roles;
	}

	
}

