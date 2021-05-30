package com.declaretonmorveux.declaretonmorveux.repository;

import com.declaretonmorveux.declaretonmorveux.model.ExpiredJwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredJwtRepository extends JpaRepository<ExpiredJwt, Integer> {
    public Integer countByJwt(String jwt);
}
