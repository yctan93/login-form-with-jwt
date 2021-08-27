package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.AppUser;
import com.example.demo.Entity.Role;
import com.example.demo.Service.AppUserService;

@RestController
@RequestMapping(path="/api")
public class AppUserController {
	@Autowired
	private AppUserService appUserService;
	
	@GetMapping(path="/users")
	public ResponseEntity<List<AppUser>> getUsers(){
		List<AppUser> allUsers = appUserService.getUsers();
		return new ResponseEntity<List<AppUser>>(allUsers, HttpStatus.OK);
	}
	
	@PostMapping(path="/user/save")
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
		AppUser newUser = appUserService.saveUser(user);
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
	
	@DeleteMapping(path="/role/removefromuser")
	public ResponseEntity<String> removeRoleFromUser(@RequestBody RoleToUserForm form){
		appUserService.removeRoleFromUser(form.getUsername(), form.getRoleName());
		return new ResponseEntity<String> ("Role removed from user", HttpStatus.OK);
	}
}

class RoleToUserForm {
	private String username;
	private String roleName;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
