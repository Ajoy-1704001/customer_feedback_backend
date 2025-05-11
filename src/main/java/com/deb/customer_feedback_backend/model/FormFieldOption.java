package com.deb.customer_feedback_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class FormFieldOption {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value; // Label for the option

    @ManyToOne
    private FormField formField;
}
