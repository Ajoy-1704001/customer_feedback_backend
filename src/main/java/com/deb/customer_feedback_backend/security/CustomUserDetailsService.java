package com.deb.customer_feedback_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deb.customer_feedback_backend.model.User;
import com.deb.customer_feedback_backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmailAndActive(username, true)
				.orElseThrow(()-> new UsernameNotFoundException("User not found with email : " + username));
		
		return UserPrincipal.build(user);
	}

}
