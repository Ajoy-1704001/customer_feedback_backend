package com.deb.customer_feedback_backend.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDTO {
	public String title;
    public String description;
    public List<FormFieldDTO> fields;
}
