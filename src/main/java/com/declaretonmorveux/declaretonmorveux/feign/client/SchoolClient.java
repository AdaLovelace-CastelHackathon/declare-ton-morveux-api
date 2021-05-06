package com.declaretonmorveux.declaretonmorveux.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "school-service", url = "${schools.datas.url}")
public interface SchoolClient {
    
    @GetMapping("/VilleMTP_MTP_Enseignements.geojson")
    public String getData();
    
}
