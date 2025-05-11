package com.deb.customer_feedback_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deb.customer_feedback_backend.model.User;
import com.deb.customer_feedback_backend.model.ERole;
import java.util.Set;




public interface UserRepository extends JpaRepository<User, Long>{
	
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByEmailAndActive(String email, boolean active);
	
	boolean existsByRolesIn(Set<ERole> roles);
}
