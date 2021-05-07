package com.declaretonmorveux.declaretonmorveux.service;

import com.declaretonmorveux.declaretonmorveux.model.ExpiredJwt;
import com.declaretonmorveux.declaretonmorveux.repository.ExpiredJwtRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpiredJwtService {
    
    @Autowired
    ExpiredJwtRepository expiredJwtRepository;

    public Integer countByJwt(String jwt){
        return expiredJwtRepository.countByJwt(jwt);
    }

    public ExpiredJwt addExpiredJwt(String jwt){
        ExpiredJwt expiredJwt = new ExpiredJwt();
        expiredJwt.setJwt(jwt);

        expiredJwtRepository.save(expiredJwt);

        return expiredJwt;
    }
}
