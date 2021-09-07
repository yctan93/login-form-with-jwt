package com.example.demo.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

	private JwtConfig jwtConfig;
	private JwtUtils jwtUtils;

	@Autowired
	public CustomAuthorizationFilter (JwtConfig jwtConfig, JwtUtils jwtUtils) {
		this.jwtConfig = jwtConfig;
		this.jwtUtils = jwtUtils;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh") || request.getServletPath().equals("/api/signup")) {
			
			filterChain.doFilter(request, response); //Pass the request to the next filter without doing anything if the user is trying to log in
			
		} else {
			
			String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					
					String username = jwtUtils.usernameFromToken(token);
					Collection<SimpleGrantedAuthority> authorities = jwtUtils.authoritiesFromToken(token);
					
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
					filterChain.doFilter(request, response);
					
				} catch (Exception exception){
					Map<String, String> error = new HashMap<>();
					error.put("error_message", exception.getMessage());
	
					response.setStatus(HttpStatus.FORBIDDEN.value());
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), error);
					
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}	
}
