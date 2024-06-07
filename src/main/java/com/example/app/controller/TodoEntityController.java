package com.example.app.controller;

import com.example.app.repository.TodoEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoEntityController {
    @Autowired
    TodoEntityRepository todoEntityRepository;

    @GetMapping("/hello")
    private ResponseEntity<String> getHello(){
        try{
            return new ResponseEntity<>("Hello", HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
