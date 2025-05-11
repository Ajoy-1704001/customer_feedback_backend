package com.deb.customer_feedback_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionDTO {
	public Long planId;
    public String externalSubscriptionId;
}
