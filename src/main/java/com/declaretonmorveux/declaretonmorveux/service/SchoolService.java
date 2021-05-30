package com.declaretonmorveux.declaretonmorveux.service;

import java.util.List;
import java.util.Optional;

import com.declaretonmorveux.declaretonmorveux.exception.DatabaseException;
import com.declaretonmorveux.declaretonmorveux.model.School;
import com.declaretonmorveux.declaretonmorveux.repository.SchoolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    public List<School> list() throws DatabaseException {
        return this.schoolRepository.findAll();
    }

    public School getById(Long id) throws DatabaseException {
        Optional<School> school = this.schoolRepository.findById(id);

        if(school.isPresent()){
            return school.get();
        } 

        return null;
    }

    public School save(School school) throws DatabaseException {
        return this.schoolRepository.save(school);
    }

    public void deleteAll() throws DatabaseException {
        this.schoolRepository.deleteAll();
    }
    
    public void deleteById(long id) throws DatabaseException {
        this.schoolRepository.deleteById(id);
    }

    public long count(){
        return this.schoolRepository.count();
    }
    
}
