package com.example.demo.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.AppUser;
import com.example.demo.Entity.Role;
import com.example.demo.Service.AppUserService;
import com.example.demo.filter.JwtConfig;
import com.example.demo.filter.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path="/api")
public class AppUserController {
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping(path="/users")
	public ResponseEntity<List<AppUser>> getUsers(){
		List<AppUser> allUsers = appUserService.getUsers();
		return new ResponseEntity<List<AppUser>>(allUsers, HttpStatus.OK);
	}
	
	@PostMapping(path="/user")
	public ResponseEntity<AppUser> getUser(@RequestBody AppUser userDetails){
		AppUser user = appUserService.getUser(userDetails.getUsername());
		return new ResponseEntity<AppUser>(user, HttpStatus.OK);
	}
	
	@PostMapping(path="/signup")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser userDetails){
		AppUser newUser = appUserService.saveUser(userDetails);
		return new ResponseEntity<AppUser>(newUser, HttpStatus.CREATED);
	}
	
	@PostMapping(path="/user/role")
	public ResponseEntity<Role> saveRole(@RequestBody Role role){
		Role newRole = appUserService.saveRole(role);
		return new ResponseEntity<Role>(newRole, HttpStatus.OK);
	}
	
	@PostMapping(path="/role/addtouser")
	public ResponseEntity<String> assignRoleToUser(@RequestBody RoleToUserForm form){
		appUserService.assignRoleToUser(form.getUsername(), form.getRoleName());
		return new ResponseEntity<String> ("Role assigned to user", HttpStatus.OK);
	}
	
	@PostMapping(path="token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				
				String username = jwtUtils.usernameFromToken(refresh_token);
	
				AppUser user = appUserService.getUser(username);
				
				Map<String, String> tokens = new HashMap<>();
				tokens.put("access_token", jwtUtils.generateAccessToken(user));
				tokens.put("refresh_token", refresh_token);
				
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), tokens);
								
			} catch (Exception exception){
				Map<String, String> error = new HashMap<>();
				error.put("error_message", exception.getMessage());

				response.setStatus(HttpStatus.FORBIDDEN.value());
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
	
	@DeleteMapping(path="/role/removefromuser")
	public ResponseEntity<String> removeRoleFromUser(@RequestBody RoleToUserForm form){
		appUserService.removeRoleFromUser(form.getUsername(), form.getRoleName());
		return new ResponseEntity<String> ("Role removed from user", HttpStatus.OK);
	}
	
	@PutMapping(path="user/update")
	public ResponseEntity<AppUser> updateUser(@RequestBody AppUser userDetails){
		AppUser updatedUser = appUserService.updateUser(userDetails.getUsername(), userDetails);
		return new ResponseEntity<AppUser> (updatedUser, HttpStatus.OK);
	}
	
	
}


