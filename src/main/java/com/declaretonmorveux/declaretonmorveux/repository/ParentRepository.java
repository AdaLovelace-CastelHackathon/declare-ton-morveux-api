package com.declaretonmorveux.declaretonmorveux.repository;

import java.util.Optional;

import com.declaretonmorveux.declaretonmorveux.model.Parent;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long>{

    public Optional<Parent> findByUsername(String username);
    public Optional<Parent> findByEmail(String email);
    public Integer countUserByUsername(String username);
}
