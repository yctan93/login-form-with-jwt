package com.example.demo.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.AppUser;
import com.example.demo.Entity.Role;
import com.example.demo.Repository.AppUserRepository;
import com.example.demo.Repository.RoleRepository;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService{

	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<AppUser> user = appUserRepository.findByUsername(username);
		
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("User does not exists!");
		}
		
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.get().getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new User(user.get().getUsername(), user.get().getPassword(), authorities); //return spring security user
	}
	
	@Override
	public AppUser saveUser(AppUser userDetails) {
		Optional<AppUser> user = appUserRepository.findByUsername(userDetails.getUsername());
		if (user.isPresent()) {
			throw new IllegalStateException("Username is already taken!");
		}
		userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
		return appUserRepository.save(userDetails);
	}

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void assignRoleToUser(String username, String roleName) {
		Optional<AppUser> user = appUserRepository.findByUsername(username);
		if(user.isPresent()) {
			Optional<Role> role = roleRepository.findByName(roleName);
			if(role.isPresent()) {
				user.get().getRoles().add(role.get());
			}
			else {
				throw new IllegalStateException("Invalid role!");
			}
		}
		else {
			throw new IllegalStateException("Username does not exists!");
		}
	}
	
	@Override
	public void removeRoleFromUser(String username, String roleName) {
		Optional<AppUser> user = appUserRepository.findByUsername(username);
		if (user.isPresent()) {
			Optional<Role> role = roleRepository.findByName(roleName);
			if(role.isPresent()) {
				user.get().getRoles().remove(role.get());
			}
			else {
				throw new IllegalStateException("Role does not exists!");
			}
		}
		else {
			throw new IllegalStateException("Username does not exists!");
		}
	}

	@Override
	public AppUser getUser(String username) {
		Optional<AppUser> user = appUserRepository.findByUsername(username);
		if (user.isPresent()) {
			return user.get();
		}
		else {
			throw new IllegalStateException("Username does not exists!");
		}
	}

	@Override
	public List<AppUser> getUsers() {
		return appUserRepository.findAll();
	}
}
