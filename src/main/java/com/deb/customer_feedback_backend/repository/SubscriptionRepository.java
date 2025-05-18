package com.deb.customer_feedback_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deb.customer_feedback_backend.model.Subscription;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
	Optional<Subscription> findByUserId(Long userId);
}
