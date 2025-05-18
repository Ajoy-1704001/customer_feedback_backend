package com.deb.customer_feedback_backend.service;

import java.math.BigDecimal;
import java.util.List;

import com.deb.customer_feedback_backend.dto.BillingHistoryDTO;
import com.deb.customer_feedback_backend.dto.BillingSummaryDTO;
import com.deb.customer_feedback_backend.dto.ProratedAmountsDTO;

public interface BillingService {
	/**
     * Get current billing summary for a subscription
     */
    BillingSummaryDTO getCurrentBillingSummary(String externalSubscriptionId);
    
    /**
     * Get billing history for a subscription with pagination
     */
    List<BillingHistoryDTO> getBillingHistory(String externalSubscriptionId, int page, int size);
    
    /**
     * Create a new subscription in the payment processor
     */
    String createSubscription(Long userId, Long externalPlanId, String paymentMethodId, 
                             boolean applyTrial, String couponCode);
    
    /**
     * Update subscription details in the payment processor
     */
    void updateSubscription(String externalSubscriptionId, String paymentMethodId, boolean autoRenew);
    
    /**
     * Cancel a subscription in the payment processor
     */
    void cancelSubscription(String externalSubscriptionId, boolean immediateEffect);
    
    /**
     * Calculate refund amount for immediate cancellation
     */
    BigDecimal calculateRefundAmount(String externalSubscriptionId);
    
    /**
     * Change subscription plan immediately in the payment processor
     */
    void changePlan(String externalSubscriptionId, Long newExternalPlanId, 
                   boolean immediateUpgrade, String promoCode);
    
    /**
     * Schedule a plan change for the next billing cycle
     */
    void scheduleChangePlan(String externalSubscriptionId, Long newExternalPlanId, String promoCode);
    
    /**
     * Get prorated amounts for a plan change
     */
    ProratedAmountsDTO getProratedAmounts(String externalSubscriptionId, Long newExternalPlanId);
}
