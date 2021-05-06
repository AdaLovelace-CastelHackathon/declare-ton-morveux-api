package com.declaretonmorveux.declaretonmorveux.feign.client;

import java.util.List;

import com.declaretonmorveux.declaretonmorveux.dto.DatasDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "school-service", url = "${schools.datas.url}")
public interface SchoolClient {
    
    @GetMapping("/VilleMTP_MTP_Enseignements.geojson")
    public String getData();
    
}
