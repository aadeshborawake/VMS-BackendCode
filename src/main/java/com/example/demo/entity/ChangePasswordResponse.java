package com.example.demo.entity;

public class ChangePasswordResponse {

	private String message;

    // Constructors
    public ChangePasswordResponse() {
    }

    public ChangePasswordResponse(String message) {
        this.message = message;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
