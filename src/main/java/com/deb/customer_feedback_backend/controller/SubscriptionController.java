package com.deb.customer_feedback_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deb.customer_feedback_backend.dto.BillingHistoryDTO;
import com.deb.customer_feedback_backend.dto.ChangePlanRequest;
import com.deb.customer_feedback_backend.dto.CreateSubscriptionRequest;
import com.deb.customer_feedback_backend.dto.PlanChangeDTO;
import com.deb.customer_feedback_backend.dto.SubscriptionCancellationDTO;
import com.deb.customer_feedback_backend.dto.SubscriptionDTO;
import com.deb.customer_feedback_backend.dto.SubscriptionDetailsDTO;
import com.deb.customer_feedback_backend.dto.UpdateSubscriptionRequest;
import com.deb.customer_feedback_backend.dto.UsageMetricsDTO;
import com.deb.customer_feedback_backend.service.SubscriptionService;
import com.deb.customer_feedback_backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	@Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getCurrentSubscription() {
        Long userId = userService.getLoggedInUserId();
        SubscriptionDetailsDTO subscriptionDetails = subscriptionService.getSubscriptionDetailsByUserId(userId);
        return ResponseEntity.ok(subscriptionDetails);
    }
    
    
    /**
     * Get subscription billing history
     */
    @GetMapping("/billing-history")
    public ResponseEntity<?> getBillingHistory(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        
        Long userId = userService.getLoggedInUserId();
        List<BillingHistoryDTO> billingHistory = subscriptionService.getBillingHistoryByUserId(userId, page, size);
        return ResponseEntity.ok(billingHistory);
    }
    
    /**
     * Get usage metrics visualization data
     */
    @GetMapping("/usage-metrics")
    public ResponseEntity<UsageMetricsDTO> getUsageMetrics() {
        Long userId = userService.getLoggedInUserId();
        UsageMetricsDTO usageMetrics = subscriptionService.getUsageMetricsByUserId(userId);
        return ResponseEntity.ok(usageMetrics);
    }
    
    /**
     * Create a new subscription
     */
    @PostMapping
    public ResponseEntity<?> createSubscription(@Valid @RequestBody CreateSubscriptionRequest request) {
        
        Long userId = userService.getLoggedInUserId();
        SubscriptionDTO subscription = subscriptionService.createSubscription(userId, request);
        return new ResponseEntity<>(subscription, HttpStatus.CREATED);
    }
    
    /**
     * Update subscription details
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSubscriptionRequest request) {
        
        Long userId = userService.getLoggedInUserId();
        SubscriptionDTO subscription = subscriptionService.updateSubscription(id, userId, request);
        return ResponseEntity.ok(subscription);
    }
    
    /**
     * Cancel subscription
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SubscriptionCancellationDTO> cancelSubscription(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean immediateEffect) {
        
        Long userId = userService.getLoggedInUserId();
        SubscriptionCancellationDTO cancellationResult = 
            subscriptionService.cancelSubscription(id, userId, immediateEffect != null && immediateEffect);
        return ResponseEntity.ok(cancellationResult);
    }
    
    /**
     * Change subscription plan
     */
    @PostMapping("/{id}/change-plan")
    public ResponseEntity<PlanChangeDTO> changePlan(
            @PathVariable Long id,
            @Valid @RequestBody ChangePlanRequest request) {
        
        Long userId = userService.getLoggedInUserId();
        PlanChangeDTO planChangeResult = subscriptionService.changePlan(id, userId, request);
        return ResponseEntity.ok(planChangeResult);
    }
}
