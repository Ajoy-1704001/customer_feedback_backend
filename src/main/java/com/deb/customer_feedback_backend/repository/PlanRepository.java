package com.deb.customer_feedback_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deb.customer_feedback_backend.model.EBillingPeriod;
import com.deb.customer_feedback_backend.model.Plan;
import java.util.List;


@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>{
	List<Plan> findByName(String name);
	
	List<Plan> findByBillingPeriod(EBillingPeriod billingPeriod);
}
