package com.declaretonmorveux.declaretonmorveux.controller;

import java.time.LocalDate;
import java.util.List;

import com.declaretonmorveux.declaretonmorveux.service.DeclarationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/declarations")
public class DeclarationController {

    @Autowired
    DeclarationService declarationService;
    
    @GetMapping("/{schoolId}")
    public ResponseEntity getDeclarationsBySchoolId(@PathVariable long schoolId){
        return ResponseEntity.ok().body(declarationService.getAllBySchoolId(schoolId));
    }
}
