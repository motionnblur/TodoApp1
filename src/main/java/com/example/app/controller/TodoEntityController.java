package com.example.app.controller;

import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
import com.example.app.service.TodoEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TodoEntityController {
    @Autowired
    protected TodoEntityService todoEntityService;

    @PutMapping("/add")
    private ResponseEntity<?> addTodo(@RequestBody TodoEntityDto todoEntityDto){
        try{
            TodoEntity todoEntitySaved = todoEntityService.saveTodoEntity(todoEntityDto);
            return new ResponseEntity<>(todoEntitySaved, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/hello")
    private ResponseEntity<String> getHello(){
        return new ResponseEntity<>("Hello", HttpStatus.ACCEPTED);
    }
}
