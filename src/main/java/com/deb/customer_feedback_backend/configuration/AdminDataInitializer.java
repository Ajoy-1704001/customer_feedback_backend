package com.deb.customer_feedback_backend.configuration;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.deb.customer_feedback_backend.model.ERole;
import com.deb.customer_feedback_backend.model.User;
import com.deb.customer_feedback_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AdminDataInitializer implements CommandLineRunner{
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.user.name}")
    private String adminName;

    @Value("${app.user.email}")
    private String adminEmail;

    @Value("${app.user.password}")
    private String adminPassword;

	
	@Override
	public void run(String... args) throws Exception {
		try {
            if(!userRepository.existsByRolesIn(Set.of(ERole.ROLE_SUPER_ADMIN))) {
                User user = User.builder()
                        .name(adminName)
                        .email(adminEmail)
                        .active(true)
                        .password(passwordEncoder.encode(adminPassword))
                        .roles(Set.of(ERole.ROLE_SUPER_ADMIN))
                        .build();
                userRepository.saveAndFlush(user);
            }
        } catch (Exception e) {
            log.info("Exception {} has occurred while creating Admin using runner class.", e.getMessage());
        }
	}

}
