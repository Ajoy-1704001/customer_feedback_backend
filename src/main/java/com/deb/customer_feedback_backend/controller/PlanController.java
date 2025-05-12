package com.deb.customer_feedback_backend.controller;

import java.util.List;
import java.util.Map;

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

import com.deb.customer_feedback_backend.model.EBillingPeriod;
import com.deb.customer_feedback_backend.model.Plan;
import com.deb.customer_feedback_backend.service.PlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plan")
public class PlanController {
	@Autowired
    private PlanService planService;
    
    @PostMapping
    public ResponseEntity<Plan> createPlan(@Valid @RequestBody Plan plan) {
        Plan newPlan = planService.createPlan(plan);
        return new ResponseEntity<>(newPlan, HttpStatus.CREATED);
    }
    

    @GetMapping
    public ResponseEntity<List<Plan>> getAllPlans() {
        List<Plan> plans = planService.getAllPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Plan> getPlanById(@PathVariable Long id) {
        Plan plan = planService.getPlanById(id);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }
    
    @GetMapping("/by-name")
    public ResponseEntity<List<Plan>> getPlansByName(@RequestParam String name) {
        List<Plan> plans = planService.getPlansByName(name);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
    
    @GetMapping("/by-billing-period")
    public ResponseEntity<List<Plan>> getPlansByBillingPeriod(@RequestParam EBillingPeriod billingPeriod) {
        List<Plan> plans = planService.getPlansByBillingPeriod(billingPeriod);
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @Valid @RequestBody Plan planDetails) {
        Plan updatedPlan = planService.updatePlan(id, planDetails);
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok(Map.of("deleted", Boolean.TRUE));
    }
}
