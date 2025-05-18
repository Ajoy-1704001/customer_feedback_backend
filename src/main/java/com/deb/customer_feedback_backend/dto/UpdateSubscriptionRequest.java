package com.deb.customer_feedback_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubscriptionRequest {
	private String paymentMethodId;
    private boolean autoRenew;
}
