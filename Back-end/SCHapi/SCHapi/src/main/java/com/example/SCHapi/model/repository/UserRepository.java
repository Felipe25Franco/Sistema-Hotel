package com.example.SCHapi.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SCHapi.model.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}