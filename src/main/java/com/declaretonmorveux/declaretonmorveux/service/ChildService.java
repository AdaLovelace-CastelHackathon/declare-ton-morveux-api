package com.declaretonmorveux.declaretonmorveux.service;

import java.time.LocalDate;
import java.util.List;

import com.declaretonmorveux.declaretonmorveux.exception.DatabaseException;
import com.declaretonmorveux.declaretonmorveux.model.Child;
import com.declaretonmorveux.declaretonmorveux.model.Declaration;
import com.declaretonmorveux.declaretonmorveux.repository.ChildRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {
    
    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private DeclarationService declarationService;

    public List<Child> getAllChildren() throws DatabaseException {
        return this.childRepository.findAll();
    }

    public Child getById(Long id) throws DatabaseException {
        return this.childRepository.findById(id).get();
    }

    public Child setIsSickAndIsContagiousByChildId(boolean isSick, boolean isContagious, long childId, Child childToUpdate) throws DatabaseException{


        if(isSick && !childToUpdate.isSick()){
            LocalDate now = LocalDate.now();
            Declaration declaration = new Declaration();
            
            declaration.setContagious(isContagious);
            declaration.setDate(now);
            declaration.setSchoolId(childToUpdate.getSchool().getId());

            declarationService.save(declaration);

            childToUpdate.setSick(isSick);
            childToUpdate.setContagious(isContagious);
            childToUpdate.setLastDeclarationDate(now);

            return this.childRepository.save(childToUpdate);
        }


            

        return null;
    }

    public Child save(Child child) throws DatabaseException{
        LocalDate now = LocalDate.now();
        child.setLastDeclarationDate(now);
        
        return this.childRepository.save(child);
    }

    public List<Child> getByParentId(Long id) throws DatabaseException {
        return this.childRepository.getByParentId(id);
    }

    public List<Child> getBySchoolId(Long schoolId) throws DatabaseException{
        return this.childRepository.getBySchoolId(schoolId);
    }

    public Integer countByIsSick() throws DatabaseException{
        return this.childRepository.countByIsSick(true);
    }

    public Integer countByIsSickAndIsContagious(boolean isSick, boolean isContagious){
        return this.childRepository.countByIsSickAndIsContagious(isSick, isContagious);
    }

    public Integer countIsSickBySchoolId(Long schoolId) throws DatabaseException {
        Integer count = 0;
        List<Child> childrenInTheSchool = this.getBySchoolId(schoolId);
        for(int i=0; i<childrenInTheSchool.size(); i++) {
            if(childrenInTheSchool.get(i).isSick()) {
                count += 1;
            }
        }
        return count;
    }

    public Integer countIsSickAndIsContagiousBySchoolId(Long schoolId) throws DatabaseException {
        Integer count = 0;
        List<Child> childrenInTheSchool = this.getBySchoolId(schoolId);
        for(int i=0; i<childrenInTheSchool.size(); i++) {
            if(childrenInTheSchool.get(i).isSick() && childrenInTheSchool.get(i).isContagious()) {
                count += 1;
            }
        }
        return count;
    }
}
