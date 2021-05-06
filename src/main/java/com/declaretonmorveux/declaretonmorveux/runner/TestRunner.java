package com.declaretonmorveux.declaretonmorveux.runner;

import com.declaretonmorveux.declaretonmorveux.model.School;
import com.declaretonmorveux.declaretonmorveux.repository.ChildRepository;
import com.declaretonmorveux.declaretonmorveux.repository.SchoolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private ChildRepository childRepository;

    @Override
    public void run(String... args) throws Exception {
    
        System.err.println(childRepository.countByIsSick(true));
    }
    
}
