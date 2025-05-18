package com.deb.customer_feedback_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillingHistoryDTO {
	private Long id;
    private String type; // CHARGE, REFUND, CREDIT, etc.
    private LocalDate date;
    private BigDecimal amount;
    private String description;
    private String status; // PAID, FAILED, PENDING, etc.
    private String invoiceUrl;
    private String receiptUrl;
}
