package com.declaretonmorveux.declaretonmorveux.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.declaretonmorveux.declaretonmorveux.service.ParentService;

import org.springframework.beans.factory.annotation.Autowired;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    @Autowired
    private ParentService parentService;

    @Override
    public void initialize(UsernameConstraint username) {

    }

    @Override
    public boolean isValid(String usernameField, ConstraintValidatorContext cxt) {
        boolean usernameAlreadyExist = parentService.countUserByUsername(usernameField) > 0;
        return !usernameAlreadyExist;
    }

}
