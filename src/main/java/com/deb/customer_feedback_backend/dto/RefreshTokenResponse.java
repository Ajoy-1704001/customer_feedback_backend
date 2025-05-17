package com.deb.customer_feedback_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponse {
	private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    
    public RefreshTokenResponse() {
		// TODO Auto-generated constructor stub
	}
    
    public RefreshTokenResponse(String token, String refreshToken) {
    	this.token = token;
    	this.refreshToken = refreshToken;
    }
}
