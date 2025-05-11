package com.deb.customer_feedback_backend.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FeedbackAnswer {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FeedbackResponse response;

    private String fieldLabel;
    private String value;
    
    @ElementCollection
    private List<String> values;

    // For file uploads, you can store file path or external URL
    private String fileUrl;
}
