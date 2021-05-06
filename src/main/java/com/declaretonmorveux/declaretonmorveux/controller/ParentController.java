package com.declaretonmorveux.declaretonmorveux.controller;

import com.declaretonmorveux.declaretonmorveux.model.Parent;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/parents")
public class ParentController {
    
    @GetMapping("/getParent")
    public ResponseEntity getParent(Authentication authentication){
        Parent parent = null;

        if(authentication != null){
            parent = (Parent)authentication.getPrincipal();
        }
        
        return ResponseEntity.ok(parent);
    }
}
