package com.deb.customer_feedback_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.deb.customer_feedback_backend.model.EBillingPeriod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanChangeDTO {
	private Long subscriptionId;
    private Long oldPlanId;
    private String oldPlanName;
    private Long newPlanId;
    private String newPlanName;
    private LocalDate effectiveDate;
    private BigDecimal proratedCharge;
    private BigDecimal proratedCredit;
    private BigDecimal newPrice;
    private EBillingPeriod newBillingPeriod;
}
