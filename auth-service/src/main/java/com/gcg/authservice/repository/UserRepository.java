package com.gcg.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gcg.authservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(@Param("username") String username);
	
}
