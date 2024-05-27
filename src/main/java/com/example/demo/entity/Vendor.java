package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_name", nullable = false)
    private String vendorName;

    @Column(name = "vendor_address", nullable = false)
    private String vendorAddress;

    @Column(name = "contact_person_first_name", nullable = false)
    private String contactPersonFirstName;

    @Column(name = "contact_person_last_name", nullable = false)
    private String contactPersonLastName;

    @Column(name = "contact_person_email")
    private String contactPersonEmail;

    @Column(name = "contact_person_number", nullable = false)
    private String contactPersonNumber;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore // Break circular reference
    private User user;

	public Vendor(Long id, String vendorName, String vendorAddress, String contactPersonFirstName,
			String contactPersonLastName, String contactPersonEmail, String contactPersonNumber, User user)
	{
		super();
		this.id = id;
		this.vendorName = vendorName;
		this.vendorAddress = vendorAddress;
		this.contactPersonFirstName = contactPersonFirstName;
		this.contactPersonLastName = contactPersonLastName;
		this.contactPersonEmail = contactPersonEmail;
		this.contactPersonNumber = contactPersonNumber;
		this.user = user;
	}

	public Vendor() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
