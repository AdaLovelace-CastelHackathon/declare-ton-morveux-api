package com.declaretonmorveux.declaretonmorveux.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.declaretonmorveux.declaretonmorveux.model.Declaration;
import com.declaretonmorveux.declaretonmorveux.repository.DeclarationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bytebuddy.asm.Advice.Local;

@Service
public class DeclarationService {
    @Autowired
    DeclarationRepository declarationRepository;

    public Declaration save(Declaration declaration){
        return declarationRepository.save(declaration);
    }

    public List<Declaration> getAll(){
        return declarationRepository.findAll();
    }

    public List<LocalDate> getAllBySchoolId(long schoolId){
        List<Declaration> declarations = declarationRepository.getBySchoolId(schoolId);
        List<LocalDate> dates = declarations.stream().map( e -> e.getDate()).collect(Collectors.toList());
    
        return dates;
    }
}