package com.deb.customer_feedback_backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deb.customer_feedback_backend.dto.BillingHistoryDTO;
import com.deb.customer_feedback_backend.dto.BillingSummaryDTO;
import com.deb.customer_feedback_backend.dto.ProratedAmountsDTO;

@Service
public class BillingServiceImpl implements BillingService{

	@Override
	public BillingSummaryDTO getCurrentBillingSummary(String externalSubscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BillingHistoryDTO> getBillingHistory(String externalSubscriptionId, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createSubscription(Long userId, Long externalPlanId, String paymentMethodId, boolean applyTrial,
			String couponCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSubscription(String externalSubscriptionId, String paymentMethodId, boolean autoRenew) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelSubscription(String externalSubscriptionId, boolean immediateEffect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal calculateRefundAmount(String externalSubscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePlan(String externalSubscriptionId, Long newExternalPlanId, boolean immediateUpgrade,
			String promoCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scheduleChangePlan(String externalSubscriptionId, Long newExternalPlanId, String promoCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProratedAmountsDTO getProratedAmounts(String externalSubscriptionId, Long newExternalPlanId) {
		// TODO Auto-generated method stub
		return null;
	}

}
