package com.deb.customer_feedback_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.deb.customer_feedback_backend.dto.MessageResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(exception = UsernameNotFoundException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
	}
	
	@ExceptionHandler(exception = BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Email/Password credential not matched"));
	}
	
	@ExceptionHandler(exception = ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Requested object Not found"));
	}
}
