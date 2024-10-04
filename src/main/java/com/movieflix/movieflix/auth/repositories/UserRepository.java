package com.movieflix.movieflix.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieflix.movieflix.auth.entitties.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String username);
}
