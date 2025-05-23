package com.deb.customer_feedback_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDTO {
	public Long subscriptionId;
	public Long planId;
	public String planName;
	public LocalDate startDate;
	public LocalDate endDate;
	public boolean active;
    public String externalSubscriptionId;
}
