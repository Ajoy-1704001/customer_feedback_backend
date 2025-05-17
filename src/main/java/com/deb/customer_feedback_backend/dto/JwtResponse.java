package com.deb.customer_feedback_backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String name;
    private List<String> roles;
    
    public JwtResponse(String token, String refreshToken, Long id, String name, String email, List<String> roles) {
    	this.token = token;
    	this.refreshToken = refreshToken;
    	this.id = id;
    	this.name = name;
    	this.email = email;
    	this.roles = roles;
    }
}
