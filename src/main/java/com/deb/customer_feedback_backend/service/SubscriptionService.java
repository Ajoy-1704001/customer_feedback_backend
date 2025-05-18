package com.deb.customer_feedback_backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deb.customer_feedback_backend.dto.BillingHistoryDTO;
import com.deb.customer_feedback_backend.dto.BillingSummaryDTO;
import com.deb.customer_feedback_backend.dto.ChangePlanRequest;
import com.deb.customer_feedback_backend.dto.CreateSubscriptionRequest;
import com.deb.customer_feedback_backend.dto.FeatureUsageDTO;
import com.deb.customer_feedback_backend.dto.HistoricalUsageDTO;
import com.deb.customer_feedback_backend.dto.PlanChangeDTO;
import com.deb.customer_feedback_backend.dto.ProratedAmountsDTO;
import com.deb.customer_feedback_backend.dto.SubscriptionCancellationDTO;
import com.deb.customer_feedback_backend.dto.SubscriptionDTO;
import com.deb.customer_feedback_backend.dto.SubscriptionDetailsDTO;
import com.deb.customer_feedback_backend.dto.UpdateSubscriptionRequest;
import com.deb.customer_feedback_backend.dto.UsageMetricsDTO;
import com.deb.customer_feedback_backend.dto.UsageSummaryDTO;
import com.deb.customer_feedback_backend.exception.ResourceNotFoundException;
import com.deb.customer_feedback_backend.exception.UnprocessableActionException;
import com.deb.customer_feedback_backend.model.EBillingPeriod;
import com.deb.customer_feedback_backend.model.Plan;
import com.deb.customer_feedback_backend.model.Subscription;
import com.deb.customer_feedback_backend.model.User;
import com.deb.customer_feedback_backend.repository.PlanRepository;
import com.deb.customer_feedback_backend.repository.SubscriptionRepository;
import com.deb.customer_feedback_backend.repository.UserRepository;

/**
 * 
 * For next phase, we can create a recommender system to recommend whether should choose a different plan based on the past usage
 */
@Service
public class SubscriptionService {
	@Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private PlanRepository planRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BillingService billingService;
    
    @Autowired
    private UsageTrackingService usageTrackingService;
    
    /**
     * Get detailed subscription information for a user
     */
    public SubscriptionDetailsDTO getSubscriptionDetailsByUserId(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found for user"));
        
        Plan plan = subscription.getPlan();
        
        UsageSummaryDTO usageSummary = usageTrackingService.getCurrentUsageSummary(userId);
        BillingSummaryDTO billingSummary = billingService.getCurrentBillingSummary(subscription.getExternalSubscriptionId());
        
        return SubscriptionDetailsDTO.builder()
            .id(subscription.getId())
            .startDate(subscription.getStartDate())
            .endDate(subscription.getEndDate())
            .active(subscription.isActive())
            .nextBillingDate(billingSummary.getCurrentPeriodEnd().plusDays(1))
            .status(determineSubscriptionStatus(subscription))
            .plan(plan)
            .onTrial(isOnTrial(subscription))
            .trialEndDate(calculateTrialEndDate(subscription))
            .usageSummary(usageSummary)
            .billingSummary(billingSummary)
            .build();
    }
    

    /**
     * Get user's billing history with pagination
     */
    public List<BillingHistoryDTO> getBillingHistoryByUserId(Long userId, Integer page, Integer size) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found for user"));

        int pageNum = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        
        return billingService.getBillingHistory(subscription.getExternalSubscriptionId(), pageNum, pageSize);
    }
    
    /**
     * Get detailed usage metrics for visualization (last 30 days)
     */
    public UsageMetricsDTO getUsageMetricsByUserId(Long userId) {
        UsageSummaryDTO currentUsage = usageTrackingService.getCurrentUsageSummary(userId);
        List<HistoricalUsageDTO> historicalForms = usageTrackingService.getHistoricalFormsUsage(userId, 30);
        List<HistoricalUsageDTO> historicalProjects = usageTrackingService.getHistoricalProjectsUsage(userId, 30);
        
        List<FeatureUsageDTO> featureUsage = usageTrackingService.getFeatureUsageBreakdown(userId);
        
        return UsageMetricsDTO.builder()
            .currentUsage(currentUsage)
            .historicalForms(historicalForms)
            .historicalProjects(historicalProjects)
            .featureUsage(featureUsage)
            .build();
    }
    
    /**
     * Create a new subscription
     */
    @Transactional
    public SubscriptionDTO createSubscription(Long userId, CreateSubscriptionRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            
        Plan plan = planRepository.findById(request.getPlanId())
            .orElseThrow(() -> new ResourceNotFoundException("Plan not found"));
        
        Optional<Subscription> existingSubscription = subscriptionRepository.findByUserId(userId);
        if (existingSubscription.isPresent() && existingSubscription.get().isActive()) {
            throw new UnprocessableActionException("User already has an active subscription");
        }
        
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setActive(true);
        subscription.setStartDate(LocalDate.now());
        
        if (!plan.isFreePlan()) {
            subscription.setEndDate(calculateEndDate(LocalDate.now(), plan.getBillingPeriod()));
        }
        
        boolean applyingTrial = request.isApplyTrial() && plan.isTrialEnabled();
        
        // Create subscription in payment processor if not a free plan
        if (!plan.isFreePlan()) {
            String externalSubscriptionId = billingService.createSubscription(
                user.getId(),
                plan.getExternalPlanId(),
                request.getPaymentMethodId(),
                applyingTrial,
                request.getCouponCode()
            );
            subscription.setExternalSubscriptionId(externalSubscriptionId);
        }
        
        subscription = subscriptionRepository.save(subscription);
        
        return convertToSubscriptionDTO(subscription);
    }
    
    /**
     * Update an existing subscription
     */
    @Transactional
    public SubscriptionDTO updateSubscription(Long id, Long userId, UpdateSubscriptionRequest request) {
        Subscription subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
            
        if (!subscription.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User does not have access to this subscription");
        }
        
        if (!subscription.getPlan().isFreePlan() && subscription.getExternalSubscriptionId() != null) {
            billingService.updateSubscription(
                subscription.getExternalSubscriptionId(),
                request.getPaymentMethodId(),
                request.isAutoRenew()
            );
        }
        
        subscription = subscriptionRepository.save(subscription);
        
        return convertToSubscriptionDTO(subscription);
    }
    
    
    @Transactional
    public SubscriptionCancellationDTO cancelSubscription(Long id, Long userId, boolean immediateEffect) {
        Subscription subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User does not have access to this subscription");
        }
        
        LocalDate effectiveEndDate;
        BigDecimal refundAmount = BigDecimal.ZERO;
        
        // Process cancellation in payment processor if not a free plan
        if (!subscription.getPlan().isFreePlan() && subscription.getExternalSubscriptionId() != null) {
            billingService.cancelSubscription(
                subscription.getExternalSubscriptionId(),
                immediateEffect
            );
            
            // Calculate refund amount if immediate cancellation
            if (immediateEffect) {
                refundAmount = billingService.calculateRefundAmount(subscription.getExternalSubscriptionId());
            }
        }
        
        if (immediateEffect) {
            effectiveEndDate = LocalDate.now();
            subscription.setEndDate(effectiveEndDate);
            subscription.setActive(false);
        } else {
            // End date will be the current billing period end
            effectiveEndDate = subscription.getEndDate();
        }
        
        subscriptionRepository.save(subscription);
        
        return SubscriptionCancellationDTO.builder()
            .subscriptionId(subscription.getId())
            .immediateEffect(immediateEffect)
            .effectiveEndDate(effectiveEndDate)
            .refundAmount(refundAmount)
            .build();
    }
    

    @Transactional
    public PlanChangeDTO changePlan(Long id, Long userId, ChangePlanRequest request) {
        Subscription subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
            
        if (!subscription.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("User does not have access to this subscription");
        }
        
        Plan oldPlan = subscription.getPlan();
        Plan newPlan = planRepository.findById(request.getNewPlanId())
            .orElseThrow(() -> new ResourceNotFoundException("New plan not found"));
            
        // Calculate prorations and effective date
        LocalDate effectiveDate;
        BigDecimal proratedCharge = BigDecimal.ZERO;
        BigDecimal proratedCredit = BigDecimal.ZERO;
        
        if (request.isImmediateUpgrade()) {
            effectiveDate = LocalDate.now();
            
            // Process plan change in payment processor if not switching between free plans
            if (!oldPlan.isFreePlan() || !newPlan.isFreePlan()) {
                // Process plan change in payment processor
                billingService.changePlan(
                    subscription.getExternalSubscriptionId(),
                    newPlan.getExternalPlanId(),
                    request.isImmediateUpgrade(),
                    request.getPromoCode()
                );
                
                // Get proration details
                ProratedAmountsDTO proratedAmounts = billingService.getProratedAmounts(
                    subscription.getExternalSubscriptionId(),
                    newPlan.getExternalPlanId()
                );
                
                proratedCharge = proratedAmounts.getProratedCharge();
                proratedCredit = proratedAmounts.getProratedCredit();
            }
            
            subscription.setPlan(newPlan);
            
            if (!newPlan.isFreePlan()) {
                subscription.setEndDate(calculateEndDate(effectiveDate, newPlan.getBillingPeriod()));
            }
            
            subscriptionRepository.save(subscription);
        } else {
            effectiveDate = subscription.getEndDate().plusDays(1);
            billingService.scheduleChangePlan(
                subscription.getExternalSubscriptionId(),
                newPlan.getExternalPlanId(),
                request.getPromoCode()
            );
        }
        
        return PlanChangeDTO.builder()
            .subscriptionId(subscription.getId())
            .oldPlanId(oldPlan.getId())
            .oldPlanName(oldPlan.getName())
            .newPlanId(newPlan.getId())
            .newPlanName(newPlan.getName())
            .effectiveDate(effectiveDate)
            .proratedCharge(proratedCharge)
            .proratedCredit(proratedCredit)
            .newPrice(newPlan.getPrice())
            .newBillingPeriod(newPlan.getBillingPeriod())
            .build();
    }
    
    // Helper methods
    
    private SubscriptionDTO convertToSubscriptionDTO(Subscription subscription) {
        return SubscriptionDTO.builder()
            .subscriptionId(subscription.getId())
            .planId(subscription.getPlan().getId())
            .planName(subscription.getPlan().getName())
            .startDate(subscription.getStartDate())
            .endDate(subscription.getEndDate())
            .active(subscription.isActive())
            .externalSubscriptionId(subscription.getExternalSubscriptionId())
            .build();
    }
    
    private LocalDate calculateEndDate(LocalDate startDate, EBillingPeriod billingPeriod) {
        switch (billingPeriod) {
            case MONTHLY:
                return startDate.plusMonths(1).minusDays(1);
            case YEARLY:
                return startDate.plusYears(1).minusDays(1);
            default:
                return null;
        }
    }
    
    private String determineSubscriptionStatus(Subscription subscription) {
        if (!subscription.isActive()) {
            return "CANCELED";
        }
        
        if (isOnTrial(subscription)) {
            return "TRIAL";
        }
        
        // Add additional status checks (past due, etc.) based on billing service info
        
        return "ACTIVE";
    }
    
    private boolean isOnTrial(Subscription subscription) {
        Plan plan = subscription.getPlan();
        
        if (!plan.isTrialEnabled() || plan.getTrialPeriod() == null) {
            return false;
        }
        
        LocalDate trialEndDate = calculateTrialEndDate(subscription);
        return trialEndDate != null && !LocalDate.now().isAfter(trialEndDate);
    }
    
    private LocalDate calculateTrialEndDate(Subscription subscription) {
        Plan plan = subscription.getPlan();
        
        if (!plan.isTrialEnabled() || plan.getTrialPeriod() == null) {
            return null;
        }
        
        return subscription.getStartDate().plusDays(plan.getTrialPeriod());
    }

}
