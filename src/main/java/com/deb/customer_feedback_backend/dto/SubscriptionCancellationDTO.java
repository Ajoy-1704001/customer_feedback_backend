package com.deb.customer_feedback_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionCancellationDTO {
	private Long subscriptionId;
    private boolean immediateEffect;
    private LocalDate effectiveEndDate;
    private BigDecimal refundAmount;
    private String cancellationReason;
}
