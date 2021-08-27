package com.example.demo.Entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="app_users")
public class AppUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private String firstname;
	
	@Column(nullable=false)
	private String lastname;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private Date dob;
	
	private String address;
	
	@ManyToMany(fetch = FetchType.EAGER) //EAGER will load all of the roles and user at the same time
	@JoinColumn(name="role_id")
	private Collection<Role> roles = new ArrayList<>();
	
	@CreationTimestamp
	private Date CREATED_DATE;
	@UpdateTimestamp
	private Date UPDATED_DATE;
	
	public AppUser() {
		
	}
	
	public AppUser (String username,
					String password,
					String firstname,
				 	String lastname,
				 	String email,
				 	Date dob,
				 	String address,	 	
				 	Collection<Role> roles,
				 	Date CREATED_DATE,
				 	Date UPDATED_DATE
				 	){
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.dob = dob;
		this.address = address;
		this.roles = roles;
		this.CREATED_DATE = CREATED_DATE;
		this.UPDATED_DATE = UPDATED_DATE;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty(access = Access.WRITE_ONLY)
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}
	
	public void setRole(Collection<Role> roles) {
		this.roles = roles;
	}
}
