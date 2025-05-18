package com.deb.customer_feedback_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageSummaryDTO {
	private Integer formsUsed;
    private Integer maxForms;
    
    private Integer projectsUsed;
    private Integer maxProjects;
}
