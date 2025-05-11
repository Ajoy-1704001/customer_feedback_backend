package com.deb.customer_feedback_backend.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FeedbackResponse {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Form form;

    private LocalDateTime submittedAt;
    private String submittedBy;

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL)
    private List<FeedbackAnswer> answers;

    @PrePersist
    protected void onCreate() {
        this.submittedAt = LocalDateTime.now();
    }
}
