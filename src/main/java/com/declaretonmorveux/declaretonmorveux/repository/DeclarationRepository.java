package com.declaretonmorveux.declaretonmorveux.repository;

import java.util.List;

import com.declaretonmorveux.declaretonmorveux.model.Declaration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeclarationRepository extends JpaRepository<Declaration, Long>{
    public List<Declaration> getBySchoolId(long schoolId);
}
