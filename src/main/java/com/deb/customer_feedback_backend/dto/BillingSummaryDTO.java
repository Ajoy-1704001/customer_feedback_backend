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
public class BillingSummaryDTO {
	private BigDecimal currentPeriodCharges;
    private LocalDate currentPeriodStart;
    private LocalDate currentPeriodEnd;
    private String paymentMethod;
    private LocalDate lastPaymentDate;
    private BigDecimal lastPaymentAmount;
}
