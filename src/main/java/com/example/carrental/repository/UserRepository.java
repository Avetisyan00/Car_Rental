package com.example.carrental.repository;

import com.example.carrental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByEmailAndVerifyToken(String email, String token);
}
