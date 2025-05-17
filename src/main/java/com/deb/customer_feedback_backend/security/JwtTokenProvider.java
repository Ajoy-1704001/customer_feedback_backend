package com.deb.customer_feedback_backend.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
    private final String jwtSecret;
    private final long jwtExpirationInMs;
    private final SecretKey secretKey;
    
    public JwtTokenProvider(@Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.expiration}") long jwtExpirationInMs) {
    	this.jwtSecret = jwtSecret;
        this.jwtExpirationInMs = jwtExpirationInMs;
    	byte[] keyBytes = jwtSecret.getBytes();
    	this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }
  
    
    public String generateJwt(Authentication authentication) {
    	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    	return Jwts.builder()
    			.subject(userPrincipal.getEmail())
    			.claim("roles", userPrincipal.getAuthorities())
    			.issuedAt(new Date())
    			.expiration(new Date((new Date()).getTime() + jwtExpirationInMs))
    			.signWith(secretKey)
    			.compact();
    }
    
    
    public String getEmailFromJWT(String token) {
    	Jws<Claims> jws = Jwts.parser()
    			.verifyWith(secretKey)
    			.build()
    			.parseSignedClaims(token);

        return jws.getPayload().getSubject();
    }
    
    public boolean validateToken(String token) {
    	try {
			Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token);
			
		return true;
		} catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
    	return false;
    }
}
