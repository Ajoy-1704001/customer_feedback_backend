package com.deb.customer_feedback_backend.helper;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.deb.customer_feedback_backend.exception.TokenRefreshException;
import com.deb.customer_feedback_backend.model.RefreshToken;
import com.deb.customer_feedback_backend.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenHelper {
	@Value("${app.jwt.refresh-token-expiration}")
    private Long refreshTokenDurationMs;
	
	@Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    public String createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        Optional<RefreshToken> existingToken = refreshTokenRepository.findTopByUserIdOrderByCreatedAtDesc(userId);

        if (existingToken.isPresent()) {
            refreshTokenRepository.delete(existingToken.get());
        }

        refreshToken.setUserId(userId);;
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.saveAndFlush(refreshToken);
        return refreshToken.getToken();
    }
    
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new login request");
        }

        return token;
    }
    
    public Optional<RefreshToken> findByRefreshToken(String token) {
    	return refreshTokenRepository.findByToken(token);
    }
    
    

    public void invalidateRefreshToken(RefreshToken refreshToken) {
    	refreshTokenRepository.delete(refreshToken);
    }
    
    
    @Transactional
    public void invalidateAllRefreshTokenByUserId(Long userId) {
    	refreshTokenRepository.deleteByUserId(userId);
    }
}
