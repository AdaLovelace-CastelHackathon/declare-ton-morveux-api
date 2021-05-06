package com.declaretonmorveux.declaretonmorveux.repository;

import java.util.List;

import com.declaretonmorveux.declaretonmorveux.model.Child;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Long>{
    
    List<Child> getByParentId(Long id);
    List<Child> getBySchoolId(Long id);
}
