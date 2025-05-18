package com.deb.customer_feedback_backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProratedAmountsDTO {
	private BigDecimal proratedCharge;
    private BigDecimal proratedCredit;
    private BigDecimal netAmount;
}
