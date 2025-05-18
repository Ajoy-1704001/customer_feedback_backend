package com.deb.customer_feedback_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deb.customer_feedback_backend.dto.FeatureUsageDTO;
import com.deb.customer_feedback_backend.dto.HistoricalUsageDTO;
import com.deb.customer_feedback_backend.dto.UsageSummaryDTO;

@Service
public class UsageTrackingServiceImpl implements UsageTrackingService{

	@Override
	public UsageSummaryDTO getCurrentUsageSummary(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HistoricalUsageDTO> getHistoricalFormsUsage(Long userId, int days) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HistoricalUsageDTO> getHistoricalProjectsUsage(Long userId, int days) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FeatureUsageDTO> getFeatureUsageBreakdown(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void trackFormCreation(Long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trackProjectCreation(Long userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void trackFeatureUsage(Long userId, String featureName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasReachedFormLimit(Long userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasReachedProjectLimit(Long userId) {
		// TODO Auto-generated method stub
		return false;
	}

}
