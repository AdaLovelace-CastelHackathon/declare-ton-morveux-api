package com.declaretonmorveux.declaretonmorveux.repository;

import java.util.Optional;

import com.declaretonmorveux.declaretonmorveux.model.ExpiredJwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredJwtRepository extends JpaRepository<ExpiredJwt, Integer> {
    public Integer countByJwt(String jwt);
}
