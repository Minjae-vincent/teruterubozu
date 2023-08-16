package com.teruterubozu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teruterubozu.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
  Users findByEmailAndPassword(String email, String password);

  Users findByEmail(String email);

  boolean existsUsersByEmail(String email);
}
