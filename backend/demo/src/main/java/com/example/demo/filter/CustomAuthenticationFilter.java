package com.example.demo.filter;

import java.io.IOException;
import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	@Autowired
	private AuthenticationManager authenticationMangager;
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationMangager) {
		this.authenticationMangager = authenticationMangager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
		try {
			UsernamePasswordAuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthenticationRequest.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
			return authenticationMangager.authenticate(authenticationToken);
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		User user = (User) authResult.getPrincipal();
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		
		String access_token = Jwts.builder()
								  .setSubject(user.getUsername())
								  .claim("roles", user.getAuthorities())
								  .setIssuedAt(new Date(0))
								  .setExpiration(Date.valueOf(LocalDate.now().plusDays(1)))
								  .signWith(key)
								  .compact();
		
		String refresh_token = Jwts.builder()
								   .setSubject(user.getUsername())
								   .setIssuedAt(new Date(0))
								   .setExpiration(Date.valueOf(LocalDate.now().plusWeeks(1)))
								   .signWith(key)
								   .compact();
		
		response.setHeader("access_token", access_token);
		response.setHeader("refresh_token", refresh_token);
	}
}

class UsernamePasswordAuthenticationRequest {
	private String username;
	private String password;
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
