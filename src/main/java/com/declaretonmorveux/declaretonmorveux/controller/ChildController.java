package com.declaretonmorveux.declaretonmorveux.controller;

import java.util.HashMap;
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
    public ResponseEntity<HashMap<String, Integer>> getSick() {
        HashMap<String, Integer> sickDatas = new HashMap<String, Integer>();

        try {
            Integer numberOfSick = this.childService.countByIsSick();
            Integer numberOfContagious = this.childService.countByIsSickAndIsContagious(true, true);
            Integer numberOfNonContagious = this.childService.countByIsSickAndIsContagious(true, false);

            sickDatas.put("sick", numberOfSick);
            sickDatas.put("contagious", numberOfContagious);
            sickDatas.put("nonContagious", numberOfNonContagious);

            return ResponseEntity.ok(sickDatas);
        } catch (DatabaseException e) {
            return ResponseEntity.notFound().build();
        }    
    }

    @GetMapping(value = "/countIsSickBySchool/{schoolId}")
    public ResponseEntity<?> countIsSickBySchoolId(@PathVariable Long schoolId) {
        try {
            return new ResponseEntity<Integer>(this.childService.countIsSickBySchoolId(schoolId), HttpStatus.OK);
        } catch (DatabaseException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/countIsSickAndIsContagiousBySchool/{schoolId}")
    public ResponseEntity<?> countIsSickAndIsContagiousBySchoolId(@PathVariable Long schoolId) {
        try {
            return new ResponseEntity<Integer>(this.childService.countIsSickAndIsContagiousBySchoolId(schoolId), HttpStatus.OK);
        } catch (DatabaseException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
