package com.deb.customer_feedback_backend.dto;

import java.time.LocalDate;

import com.deb.customer_feedback_backend.model.Plan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDetailsDTO {
	private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    private LocalDate nextBillingDate;
    private String status;
    
    private Plan plan;
    
    private boolean onTrial;
    private LocalDate trialEndDate;
    
    private UsageSummaryDTO usageSummary;
    
    private BillingSummaryDTO billingSummary;
    
}
