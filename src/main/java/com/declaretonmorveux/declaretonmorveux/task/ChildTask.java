package com.declaretonmorveux.declaretonmorveux.task;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.declaretonmorveux.declaretonmorveux.model.Child;
import com.declaretonmorveux.declaretonmorveux.repository.ChildRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ChildTask {

    @Autowired
    private ChildRepository childRepository;

    @Scheduled(cron = "@midnight")
    public void update() {
        List<Child> childs = childRepository.getAllByIsSick(true);

        for (Child child : childs) {
            LocalDate currentDeclarationDate = child.getLastDeclarationDate();
            

            if(currentDeclarationDate != null){
                LocalDate limitDate = LocalDate.ofEpochDay(currentDeclarationDate.toEpochDay());
                limitDate.plusDays(5);

                if (LocalDate.now().isAfter(limitDate)) {
                    child.setSick(false);
                    child.setContagious(false);
    
                    System.err.println("A child passed to sick = false");
                }
            } else {
                child.setSick(false);
                child.setContagious(false);
            }

        }
        childRepository.saveAll(childs);
    }
}
