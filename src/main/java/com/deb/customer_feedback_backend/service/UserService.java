package com.deb.customer_feedback_backend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deb.customer_feedback_backend.security.UserPrincipal;

@Service
public class UserService {
	public UserPrincipal getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null) {
			return null;
		}
		return (UserPrincipal) authentication.getPrincipal();
	}
	
	public Long getLoggedInUserId() {
		UserPrincipal currentUser = getLoggedInUser();
		if(currentUser == null)
			return null;
		return currentUser.getId();
	}
}
