package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	Optional<AppUser> findByUsername(String username);
}
