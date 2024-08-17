package com.example.app.controller;

import com.example.app.dto.UserEntityDto;
import com.example.app.entity.UserEntity;
import com.example.app.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    protected UserEntityService userEntityService;

    @PutMapping
    private ResponseEntity<?> addUser(@RequestBody UserEntityDto userEntityDto) {
        try{
            UserEntity userEntitySaved = userEntityService.saveUserEntity(userEntityDto);
            return new ResponseEntity<>(userEntitySaved, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    private ResponseEntity<?> getUser(@RequestParam String userName) {
        try{
            UserEntity userEntity = userEntityService.getUserEntity(userName);
            return new ResponseEntity<>(userEntity, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
