package com.example.app.controller;

import com.example.app.dto.TodoEntityDto;
import com.example.app.entity.TodoEntity;
import com.example.app.service.TodoEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("todo")
public class TodoEntityController {
    @Autowired
    protected TodoEntityService todoEntityService;

    @PutMapping
    private ResponseEntity<?> addTodo(@RequestBody TodoEntityDto todoEntityDto){
        try{
            TodoEntity todoEntitySaved = todoEntityService.saveTodoEntity(todoEntityDto);
            return new ResponseEntity<>(todoEntitySaved, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    private ResponseEntity<?> getTodo(@RequestParam String todoName){
        try{
            TodoEntity todoEntity = todoEntityService.getTodoEntity(todoName);
            return new ResponseEntity<>(todoEntity, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    private ResponseEntity<?> changeTodo(@RequestBody TodoEntityDto todoEntityDto){
        try{
            todoEntityService.updateTodoEntity(todoEntityDto);
            return new ResponseEntity<>("successfully updated", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping
    private ResponseEntity<?> deleteTodo(@RequestParam String todoName){
        try{
            TodoEntity todoEntityDeleted = todoEntityService.deleteTodoEntity(todoName);
            return new ResponseEntity<>(todoEntityDeleted, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addItem")
    private ResponseEntity<?> addItem(@RequestParam String todoName, @RequestParam String item){
        try{
            todoEntityService.addTodoItem(todoName, item);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/deleteItem")
    private ResponseEntity<?> deleteItem(@RequestParam String todoName, @RequestParam String item){
        try{
            todoEntityService.deleteTodoItem(todoName, item);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
