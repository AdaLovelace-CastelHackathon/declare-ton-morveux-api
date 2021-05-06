package com.declaretonmorveux.declaretonmorveux.runner;

import com.declaretonmorveux.declaretonmorveux.feign.client.SchoolClient;
import com.declaretonmorveux.declaretonmorveux.model.School;
import com.declaretonmorveux.declaretonmorveux.service.ChildService;
import com.declaretonmorveux.declaretonmorveux.service.SchoolService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatasRunner implements CommandLineRunner{

    @Autowired
    SchoolClient schoolClient;

    @Autowired
    SchoolService schoolService;

    @Autowired
    ChildService childService;

    @Override
    public void run(String... args) throws Exception {

        String datas = schoolClient.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(datas).get("features");

        for(int i = 0; i < jsonNode.size(); i++){
            long schooldId = i + 1;
            boolean schoolHasChild = (childService.getBySchoolId(schooldId).size() > 0);
            String schoolName = jsonNode.get(i).get("properties").get("libel").toString();

            if(!schoolHasChild && !schoolName.isEmpty()){
                schoolService.deleteById(schooldId);

                School school = new School();
                school.setId(schooldId);
                school.setName(schoolName);

                schoolService.save(school);
            }

     
        }
      
    }
    
}
