package com.deb.customer_feedback_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubscriptionRequest {
	private Long planId;
    private String paymentMethodId;
    private boolean applyTrial;
    private String couponCode;
}
