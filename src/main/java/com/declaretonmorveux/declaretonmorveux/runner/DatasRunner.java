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
public class DatasRunner implements CommandLineRunner {

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

        for (int i = 0; i < jsonNode.size(); i++) {
            long schooldId = i + 1;
            boolean schoolHasChild = (childService.getBySchoolId(schooldId).size() > 0);
            String schoolName = jsonNode.get(i).get("properties").get("libel").asText();
            JsonNode schoolCoordinatesJsonNodes = jsonNode.get(i).get("geometry").get("coordinates");

            // if (!schoolHasChild && !schoolName.isEmpty()) {

            //     schoolService.deleteById(schooldId);

            //     School school = new School();
            //     school.setId(schooldId);
            //     school.setName(schoolName.replace("\"", ""));
            //     school.setLatitude(schoolCoordinatesJsonNodes.get(0).toString());
            //     school.setLongitude(schoolCoordinatesJsonNodes.get(1).toString());

            //     schoolService.save(school);
            // } else {
            //     School oldSchool = schoolService.getById(schooldId);

            //     if (oldSchool != null) {
            //         oldSchool.setId(schooldId);
            //         oldSchool.setName(schoolName.replace("\"", ""));
            //         oldSchool.setLatitude(schoolCoordinatesJsonNodes.get(0).toString());
            //         oldSchool.setLongitude(schoolCoordinatesJsonNodes.get(1).toString());
            //         schoolService.save(oldSchool);
            //     }

            // }

            System.out.println("School has child ? => " + schoolHasChild + " | SchoolName => " + schoolName + " | Location => "+ schoolCoordinatesJsonNodes.get(0));

        }
        System.err.println("DATAS INSERTION DONE");
    }

}
