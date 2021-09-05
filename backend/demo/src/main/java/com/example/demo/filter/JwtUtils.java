package com.example.demo.filter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.example.demo.Entity.AppUser;
import com.example.demo.Entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtUtils {

	@Autowired
	private JwtConfig jwtConfig;
	
	public JwtUtils (JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}
	
	public String generateAccessToken(User user) {
		return Jwts.builder()
				   .setSubject(user.getUsername())
				   .claim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())) // using the stream api to map permissions or roles into a list
				   .setIssuedAt(Date.valueOf(LocalDate.now()))
				   .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getAccessTokenExpirationAfterDays())))
				   .signWith(jwtConfig.getSecretKeyForSigning())
				   .compact();
	}
	
	public String generateAccessToken(AppUser user) {
		return Jwts.builder()
				   .setSubject(user.getUsername())
				   .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList())) // using the stream api to map permissions or roles into a list
				   .setIssuedAt(Date.valueOf(LocalDate.now()))
				   .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getAccessTokenExpirationAfterDays())))
				   .signWith(jwtConfig.getSecretKeyForSigning())
				   .compact();
	}
	
	public String generateRefreshToken(User user) {
		return Jwts.builder()
				   .setSubject(user.getUsername())
				   .setIssuedAt(Date.valueOf(LocalDate.now()))
				   .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getRefreshTokenExpirationAfterDays())))
				   .signWith(jwtConfig.getSecretKeyForSigning())
				   .compact();
	}
	
	public Jws<Claims> decodeToken (String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(jwtConfig.getSecretKeyForSigning())
				   .build()
				   .parseClaimsJws(token);
	}
	
	public String usernameFromToken (String token) {
		return decodeToken(token).getBody().getSubject();
	}
	
	public Collection<SimpleGrantedAuthority> authoritiesFromToken (String token){
		ArrayList<?> roles = (ArrayList<?>) decodeToken(token).getBody().get("roles");
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		});
		return authorities;
	}
	
	
}
