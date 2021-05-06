package com.declaretonmorveux.declaretonmorveux.controller;

import java.util.List;

import com.declaretonmorveux.declaretonmorveux.exception.DatabaseException;
import com.declaretonmorveux.declaretonmorveux.model.Child;
import com.declaretonmorveux.declaretonmorveux.service.ChildService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/children")
public class ChildController {

    @Autowired
    private ChildService childService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getChildById(@PathVariable Long id) {
        try {
            return new ResponseEntity<Child>(this.childService.getById(id), HttpStatus.OK);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/parent/{parentId}")
    public ResponseEntity<?> getChildByParentId(@PathVariable Long parentId) {
        try {
            return new ResponseEntity<List<Child>>(this.childService.getByParentId(parentId), HttpStatus.OK);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createChild(@RequestBody Child child) {
        try {
            return new ResponseEntity<Child>(this.childService.save(child), HttpStatus.CREATED);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateChild(@RequestBody Child child) {
        try {
            return new ResponseEntity<Child>(this.childService.save(child), HttpStatus.ACCEPTED);
        } catch (DatabaseException e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/sick")
    public Integer getNumberOfSick(){
        return this.childService.countByIsSick();
    }
}
