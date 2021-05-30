package com.declaretonmorveux.declaretonmorveux.controller;

import com.declaretonmorveux.declaretonmorveux.dto.ParentDto;
import com.declaretonmorveux.declaretonmorveux.service.ParentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;
    
    @GetMapping("/me")
    public ResponseEntity getParent(Authentication authentication){
        ParentDto parent = null;

        parentService.getParent(authentication);

        if(authentication != null){
            parent = parentService.getParent(authentication);
        }
        
        return ResponseEntity.ok(parent);
    }
}
