package com.deb.customer_feedback_backend.dto;

import java.util.List;

import com.deb.customer_feedback_backend.model.EFormFieldType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormFieldDTO {
	public String label;
    public EFormFieldType fieldType;
    public List<String> options;
}
