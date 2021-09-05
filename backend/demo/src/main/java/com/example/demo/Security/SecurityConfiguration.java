package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.filter.CustomAuthorizationFilter;
import com.example.demo.filter.JwtConfig;
import com.example.demo.filter.JwtUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private JwtConfig jwtConfig;
	private JwtUtils jwtUtils;
	
	@Autowired
	public SecurityConfiguration(UserDetailsService userDetailsService, 
								 BCryptPasswordEncoder bCryptPasswordEncoder,
								 JwtConfig jwtConfig, JwtUtils jwtUtils) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtConfig = jwtConfig; 
		this.jwtUtils = jwtUtils;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(), jwtUtils);
		CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter(jwtConfig, jwtUtils);
		
		// Customize login url
		customAuthenticationFilter.setFilterProcessesUrl("/api/login");
		
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests().antMatchers("/api/login").permitAll()
			.and()
			.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("ROLE_USER")
			.and()
			.authorizeRequests().antMatchers(HttpMethod.GET,"/api/token/refresh").permitAll()
			.and()
			.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user/save/**").hasAnyAuthority("ROLE_ADMIN")
			.and()
			.authorizeRequests().antMatchers("/api/role/**").hasAnyAuthority("ROLE_ADMIN")
			.and()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.addFilter(customAuthenticationFilter)
			.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // Make sure this filter comes before any filter
			
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
