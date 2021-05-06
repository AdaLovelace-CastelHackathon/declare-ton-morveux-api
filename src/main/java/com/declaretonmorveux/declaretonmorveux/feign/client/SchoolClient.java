package com.declaretonmorveux.declaretonmorveux.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "task-service", url = "${schools.datas.url}")
public interface SchoolClient {
    
    @GetMapping("/tasks/ofuser/{id}")
    public String getData(@PathVariable int id);
    
}
