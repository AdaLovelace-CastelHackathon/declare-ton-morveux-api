package com.declaretonmorveux.declaretonmorveux.runner;

import java.time.LocalDate;
import java.util.List;

import com.declaretonmorveux.declaretonmorveux.model.Child;
import com.declaretonmorveux.declaretonmorveux.service.ChildService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class DateRunner implements CommandLineRunner {

    @Autowired
    private ChildService childService;

    @Override
    public void run(String... args) throws Exception {

        List<Child> children = this.childService.getAllChildren();
        for(int i=0; i<children.size() ; i++) {
            if(!children.get(i).getLastDeclarationDate().equals(LocalDate.now())) {
                children.get(i).setSick(false);
                children.get(i).setContagious(false);
            }
        }
    }
}
