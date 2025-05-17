package com.deb.customer_feedback_backend.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.deb.customer_feedback_backend.model.Root;
import com.deb.customer_feedback_backend.security.UserPrincipal;

public class CustomAuditAware implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            UserPrincipal authUser = (UserPrincipal) authentication.getPrincipal();
            return Optional.of(String.valueOf(authUser.getName()).concat(Root.AUDIT_SEPERATOR).concat(authUser.getAuthoritiesAsString()));
        } catch (Exception e) {
            return Optional.empty();
        }
	}

}
