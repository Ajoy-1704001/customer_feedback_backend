package com.deb.customer_feedback_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
	@NotBlank
	private String refreshToken;
	private boolean logoutFromAllDevice = false;
}
