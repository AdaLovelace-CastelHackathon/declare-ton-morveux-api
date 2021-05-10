package com.declaretonmorveux.declaretonmorveux.repository;

import com.declaretonmorveux.declaretonmorveux.model.Declaration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeclarationRepository extends JpaRepository<Declaration, Long>{
    
}
