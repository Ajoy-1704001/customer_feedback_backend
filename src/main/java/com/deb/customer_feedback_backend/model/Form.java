package com.deb.customer_feedback_backend.model;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Form {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String publicUrl;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<FormField> fields;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<FeedbackResponse> responses;

}
