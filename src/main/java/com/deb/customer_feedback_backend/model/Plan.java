package com.deb.customer_feedback_backend.model;

import java.math.BigDecimal;
import java.util.List;

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
    private boolean freePlan;
    private boolean trialEnabled;
    private Integer trialPeriod;
    private Integer maxForms;
    private Integer maxProjects;
    @ElementCollection
    private List<String> features;
    private BigDecimal price;
    private EBillingPeriod billingPeriod;
    private Long externalPlanId;
}
