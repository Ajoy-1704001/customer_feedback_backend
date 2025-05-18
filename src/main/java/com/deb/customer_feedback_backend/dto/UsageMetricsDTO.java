package com.deb.customer_feedback_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsageMetricsDTO {
	private UsageSummaryDTO currentUsage;
    
    // Historical usage data for charts
    private List<HistoricalUsageDTO> historicalForms;
    private List<HistoricalUsageDTO> historicalProjects;
    
    // Feature usage breakdown
    private List<FeatureUsageDTO> featureUsage;
}
