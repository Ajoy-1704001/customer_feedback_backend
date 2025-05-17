package com.deb.customer_feedback_backend.dto;

import org.springframework.stereotype.Service;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Service
public class RefreshTokenRequest {
	@NotBlank
	private String refreshToken;
}
