package com.declaretonmorveux.declaretonmorveux.service;

import java.util.List;

import com.declaretonmorveux.declaretonmorveux.model.Declaration;
import com.declaretonmorveux.declaretonmorveux.repository.DeclarationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
