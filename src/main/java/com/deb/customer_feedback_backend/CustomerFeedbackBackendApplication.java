package com.deb.customer_feedback_backend;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CustomerFeedbackBackendApplication {
	
	public static long appStartTime;
	
	@GetMapping("/")
	String home() {
		return "<b>Customer Feedback SaaS</b> Application is Running since: " + new Date(appStartTime);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerFeedbackBackendApplication.class, args);
		appStartTime = System.currentTimeMillis();
	}

}
