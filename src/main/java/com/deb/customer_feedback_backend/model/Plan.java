package com.deb.customer_feedback_backend.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Plan {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int maxForms;
    private int maxResponsesPerMonth;
    private BigDecimal price;
    private String billingPeriod;
}
