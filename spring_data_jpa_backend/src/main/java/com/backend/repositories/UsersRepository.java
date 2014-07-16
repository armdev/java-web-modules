package com.backend.repositories;

import com.backend.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	Users findOne(Long id);	
        @Override
	Users save(Users customer);
}
