package com.declaretonmorveux.declaretonmorveux.service;

import java.util.List;

import com.declaretonmorveux.declaretonmorveux.exception.DatabaseException;
import com.declaretonmorveux.declaretonmorveux.model.Child;
import com.declaretonmorveux.declaretonmorveux.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {
    
    @Autowired
    private ChildRepository childRepository;

    public Child getById(Long id) throws DatabaseException {
        return this.childRepository.findById(id).get();
    }

    public Child save(Child child) throws DatabaseException {
        return this.childRepository.save(child);
    }

    public List<Child> getByParentId(Long id) throws DatabaseException {
        return this.childRepository.getByParentId(id);
    }

    public List<Child> getBySchoolId(Long id) throws DatabaseException{
        return this.childRepository.getBySchoolId(id);
    }

    public Integer countByIsSick(){
        return this.childRepository.countByIsSick(true);
    }
}
