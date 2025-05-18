package com.deb.customer_feedback_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePlanRequest {
	private Long newPlanId;
    private boolean immediateUpgrade;
    private String promoCode;
}
