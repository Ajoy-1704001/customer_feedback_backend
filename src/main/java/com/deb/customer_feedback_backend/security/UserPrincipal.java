package com.deb.customer_feedback_backend.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.deb.customer_feedback_backend.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrincipal implements UserDetails{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal(Long id, String name, String email, String password,
            Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserPrincipal build(User user) {
		List<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map(
                role -> {
                  return new SimpleGrantedAuthority(role.name());
                }).
        collect(Collectors.toList());
		
		return new UserPrincipal(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getPassword(),
				grantedAuthorities
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getEmail() {
		return email;
	}

}
