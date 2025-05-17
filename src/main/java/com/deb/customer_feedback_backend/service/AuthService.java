package com.deb.customer_feedback_backend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deb.customer_feedback_backend.dto.JwtResponse;
import com.deb.customer_feedback_backend.dto.LoginRequest;
import com.deb.customer_feedback_backend.dto.LogoutRequest;
import com.deb.customer_feedback_backend.dto.MessageResponse;
import com.deb.customer_feedback_backend.dto.RefreshTokenRequest;
import com.deb.customer_feedback_backend.dto.RefreshTokenResponse;
import com.deb.customer_feedback_backend.dto.SignUpRequest;
import com.deb.customer_feedback_backend.exception.TokenRefreshException;
import com.deb.customer_feedback_backend.helper.RefreshTokenHelper;
import com.deb.customer_feedback_backend.model.ERole;
import com.deb.customer_feedback_backend.model.RefreshToken;
import com.deb.customer_feedback_backend.model.User;
import com.deb.customer_feedback_backend.repository.UserRepository;
import com.deb.customer_feedback_backend.security.CustomUserDetailsService;
import com.deb.customer_feedback_backend.security.JwtTokenProvider;
import com.deb.customer_feedback_backend.security.UserPrincipal;

@Service
public class AuthService {
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private RefreshTokenHelper refreshTokenHelper;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
    	User user = userRepository.findByEmailAndActive(loginRequest.getEmail(), true)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));
    	Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateJwt(authentication);
        String refreshToken = refreshTokenHelper.createRefreshToken(user.getId());
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(
                jwt,
                refreshToken,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getName(),
                roles);
    }

    public ResponseEntity<?> registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .active(true)
                .build();

        Set<ERole> roles = new HashSet<>();
        roles.add(ERole.ROLE_USER);

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    
    public ResponseEntity<?> refreshToken(RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken = refreshTokenHelper.findByRefreshToken(requestRefreshToken)
        		.orElseThrow(()->new TokenRefreshException(requestRefreshToken, "Refresh token not found"));
        refreshToken = refreshTokenHelper.verifyExpiration(refreshToken);
        User user = userRepository.findById(refreshToken.getId())
        		.orElseThrow(()->new TokenRefreshException(requestRefreshToken, "User not found"));
        UserDetails userDetails = (UserPrincipal) customUserDetailsService.loadUserByUsername(user.getEmail());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String newJwt = jwtTokenProvider.generateJwt(authentication);
		return ResponseEntity.ok(new RefreshTokenResponse(newJwt, requestRefreshToken));
    }
    
    public ResponseEntity<?> logout(LogoutRequest request){
    	String requestRefreshToken = request.getRefreshToken();
    	RefreshToken refreshToken = refreshTokenHelper.findByRefreshToken(requestRefreshToken)
        		.orElseThrow(()->new TokenRefreshException(requestRefreshToken, "Refresh token not found"));
    	
    	if(request.isLogoutFromAllDevice()) {
    		refreshTokenHelper.invalidateAllRefreshTokenByUserId(refreshToken.getUserId());
    	} else {
    		refreshTokenHelper.invalidateRefreshToken(refreshToken);
    	}
    	return ResponseEntity.ok(new MessageResponse("Logout successful!"));
    }
}
