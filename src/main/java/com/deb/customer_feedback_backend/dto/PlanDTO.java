package com.deb.customer_feedback_backend.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanDTO {
	public String name;
    public String description;
    public int maxForms;
    public int maxResponsesPerMonth;
    public BigDecimal price;
    public String billingPeriod;
}
