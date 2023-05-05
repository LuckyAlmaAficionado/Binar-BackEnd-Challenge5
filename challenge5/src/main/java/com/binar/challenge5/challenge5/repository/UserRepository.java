package com.binar.challenge5.challenge5.repository;

import com.binar.challenge5.challenge5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query(value = "SELECT u FROM User u WHERE id = :id")
    User getById(Long id);

}
