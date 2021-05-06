package com.declaretonmorveux.declaretonmorveux.runner;

import com.declaretonmorveux.declaretonmorveux.feign.client.SchoolClient;
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

    @Override
    public void run(String... args) throws Exception {
        String datas = schoolClient.getData();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(datas);

        System.err.println(jsonNode.get("type"));
    }
    
}
