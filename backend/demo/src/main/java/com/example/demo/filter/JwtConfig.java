package com.example.demo.filter;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
	private String secretKey;
	private Integer accessTokenExpirationAfterDays;
	private Integer refreshTokenExpirationAfterDays;
	
	public JwtConfig() {
		
	}
	
	public String getSecretKey() {
		return secretKey;
	}
	
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public Integer getAccessTokenExpirationAfterDays() {
		return accessTokenExpirationAfterDays;
	}
	
	public void setAccessTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
		this.accessTokenExpirationAfterDays = tokenExpirationAfterDays;
	}
	
	public Integer getRefreshTokenExpirationAfterDays() {
		return refreshTokenExpirationAfterDays;
	}
	
	public void setRefreshTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
		this.refreshTokenExpirationAfterDays = tokenExpirationAfterDays;
	}

	public SecretKey getSecretKeyForSigning() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}
}
