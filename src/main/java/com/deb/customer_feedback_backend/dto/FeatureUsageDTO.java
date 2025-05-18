package com.deb.customer_feedback_backend.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureUsageDTO {
	private String featureName;
    private Integer usageCount;
    private LocalDate lastUsed;
}
