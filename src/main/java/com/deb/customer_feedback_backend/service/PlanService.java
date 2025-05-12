package com.deb.customer_feedback_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deb.customer_feedback_backend.exception.ResourceNotFoundException;
import com.deb.customer_feedback_backend.model.EBillingPeriod;
import com.deb.customer_feedback_backend.model.Plan;
import com.deb.customer_feedback_backend.repository.PlanRepository;

@Service
public class PlanService {
	
	@Autowired
	private PlanRepository planRepository;
	
	public Plan createPlan(Plan plan) {
        return planRepository.saveAndFlush(plan);
    }
    
    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }
    
    public Plan getPlanById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
    }
    
    public List<Plan> getPlansByName(String name) {
        return planRepository.findByName(name);
    }
    
    public List<Plan> getPlansByBillingPeriod(EBillingPeriod billingPeriod) {
        return planRepository.findByBillingPeriod(billingPeriod);
    }
    
    public Plan updatePlan(Long id, Plan planDetails) {
        Plan plan = getPlanById(id);
        
        plan.setName(planDetails.getName());
        plan.setDescription(planDetails.getDescription());
        plan.setMaxForms(planDetails.getMaxForms());
        plan.setMaxResponsesPerMonth(planDetails.getMaxResponsesPerMonth());
        plan.setPrice(planDetails.getPrice());
        plan.setBillingPeriod(planDetails.getBillingPeriod());
        
        return planRepository.saveAndFlush(plan);
    }

    public void deletePlan(Long id) {
        Plan plan = getPlanById(id);
        planRepository.delete(plan);
    }
}
