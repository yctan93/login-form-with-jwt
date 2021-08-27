package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.AppUser;
import com.example.demo.Entity.Role;

public interface AppUserService {
	AppUser saveUser(AppUser userDetails);
	Role saveRole(Role role);
	void assignRoleToUser(String username, String roleName);
	void removeRoleFromUser(String username, String roleName);
	AppUser getUser(String username);
	List<AppUser> getUsers();
}
