package com.example.demo.dto;

public class VendorRegistrationDTO {

	private String vendorName;
    private String vendorAddress;
    private String contactPersonFirstName;
    private String contactPersonLastName;
    private String contactPersonEmail;
    private String contactPersonNumber;
    private String username;
    private String password;
	public VendorRegistrationDTO(String vendorName, String vendorAddress, String contactPersonFirstName,
			String contactPersonLastName, String contactPersonEmail, String contactPersonNumber, String username,
			String password) {
		super();
		this.vendorName = vendorName;
		this.vendorAddress = vendorAddress;
		this.contactPersonFirstName = contactPersonFirstName;
		this.contactPersonLastName = contactPersonLastName;
		this.contactPersonEmail = contactPersonEmail;
		this.contactPersonNumber = contactPersonNumber;
		this.username = username;
		this.password = password;
	}
	public VendorRegistrationDTO() {
		super();
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	public String getContactPersonFirstName() {
		return contactPersonFirstName;
	}
	public void setContactPersonFirstName(String contactPersonFirstName) {
		this.contactPersonFirstName = contactPersonFirstName;
	}
	public String getContactPersonLastName() {
		return contactPersonLastName;
	}
	public void setContactPersonLastName(String contactPersonLastName) {
		this.contactPersonLastName = contactPersonLastName;
	}
	public String getContactPersonEmail() {
		return contactPersonEmail;
	}
	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}
	public String getContactPersonNumber() {
		return contactPersonNumber;
	}
	public void setContactPersonNumber(String contactPersonNumber) {
		this.contactPersonNumber = contactPersonNumber;
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
}
