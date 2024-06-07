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
        return new ResponseEntity<>("Hello", HttpStatus.ACCEPTED);
    }
}
