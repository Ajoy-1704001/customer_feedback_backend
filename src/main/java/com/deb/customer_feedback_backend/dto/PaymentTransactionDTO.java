package com.deb.customer_feedback_backend.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTransactionDTO {
	public String provider;
    public String transactionId;
    public BigDecimal amount;
    public String status;
}
