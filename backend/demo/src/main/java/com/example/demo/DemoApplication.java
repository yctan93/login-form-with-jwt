package com.example.demo;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Entity.AppUser;
import com.example.demo.Entity.Role;
import com.example.demo.Service.AppUserService;

@SpringBootApplication
@ComponentScan
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
//	@Bean
//	CommandLineRunner run(AppUserService appUserService) {
//		return args -> {
//			appUserService.saveRole(new Role("ROLE_USER"));
//			appUserService.saveRole(new Role("ROLE_MODERATOR"));
//			appUserService.saveRole(new Role("ROLE_ADMIN"));
//			
//			appUserService.saveUser(new AppUser("yctan", "123456", "Tan", "YC", "yc@gmail.com", new Date(2000-01-01), "fake street 101 #01-01", new ArrayList<>(), null, null));
//			appUserService.saveUser(new AppUser("mariotan", "123456", "Tan", "Mario", "mario@gmail.com", new Date(2001-02-02), "fake street 102 #02-02", new ArrayList<>(), null, null));
//			appUserService.saveUser(new AppUser("luigitan", "123456", "Tan", "Luigi", "luigi@gmail.com", new Date(2002-03-03), "fake street 103 #03-03", new ArrayList<>(), null, null));
//			
//			appUserService.assignRoleToUser("yctan", "ROLE_USER");
//			appUserService.assignRoleToUser("yctan", "ROLE_MODERATOR");
//			appUserService.assignRoleToUser("yctan", "ROLE_ADMIN");
//			appUserService.assignRoleToUser("mariotan", "ROLE_USER");
//			appUserService.assignRoleToUser("luigitan", "ROLE_MODERATOR");
//		};
//	}
}
