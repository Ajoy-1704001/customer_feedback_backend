package com.deb.customer_feedback_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deb.customer_feedback_backend.model.RefreshToken;



@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	Optional<RefreshToken> findTopByUserIdOrderByCreatedAtDesc(Long userId);
	
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByUserId(Long userId);
}
