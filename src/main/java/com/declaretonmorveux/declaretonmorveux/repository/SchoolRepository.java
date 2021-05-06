package com.declaretonmorveux.declaretonmorveux.repository;

import com.declaretonmorveux.declaretonmorveux.model.School;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
    
}
