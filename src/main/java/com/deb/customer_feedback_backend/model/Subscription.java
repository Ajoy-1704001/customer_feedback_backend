package com.deb.customer_feedback_backend.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Subscription {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private Plan plan;

    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;
    private String externalSubscriptionId;
}
