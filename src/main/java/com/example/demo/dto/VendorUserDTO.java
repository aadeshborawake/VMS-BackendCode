package com.example.demo.dto;

public class VendorUserDTO {
	
	private String username;
    private String password;
    private String role; 
    private String vendorName;
    private int active;
    
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
	

    public boolean isActive() {
        return active == 1; // Convert int to boolean for the isActive check
    }

    public void setActive(int active) {
        this.active = active; // Set the active status
    }

}
