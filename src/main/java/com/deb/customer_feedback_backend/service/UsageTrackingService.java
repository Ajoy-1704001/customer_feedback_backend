package com.deb.customer_feedback_backend.service;

import java.util.List;

import com.deb.customer_feedback_backend.dto.FeatureUsageDTO;
import com.deb.customer_feedback_backend.dto.HistoricalUsageDTO;
import com.deb.customer_feedback_backend.dto.UsageSummaryDTO;

public interface UsageTrackingService {
	/**
     * Get current usage summary for a user
     */
    UsageSummaryDTO getCurrentUsageSummary(Long userId);
    
    /**
     * Get historical form usage data for charts
     * @param days Number of days to look back
     */
    List<HistoricalUsageDTO> getHistoricalFormsUsage(Long userId, int days);
    
    /**
     * Get historical project usage data for charts
     * @param days Number of days to look back
     */
    List<HistoricalUsageDTO> getHistoricalProjectsUsage(Long userId, int days);
    
    /**
     * Get feature usage breakdown
     */
    List<FeatureUsageDTO> getFeatureUsageBreakdown(Long userId);
    
    /**
     * Track form creation
     */
    void trackFormCreation(Long userId);
    
    /**
     * Track project creation
     */
    void trackProjectCreation(Long userId);
    
    /**
     * Track feature usage
     */
    void trackFeatureUsage(Long userId, String featureName);
    
    /**
     * Check if user has reached form limit
     */
    boolean hasReachedFormLimit(Long userId);
    
    /**
     * Check if user has reached project limit
     */
    boolean hasReachedProjectLimit(Long userId);
}
