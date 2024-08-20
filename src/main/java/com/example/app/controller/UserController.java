package com.example.app.controller;

import com.example.app.dto.UserEntityDto;
import com.example.app.entity.UserEntity;
import com.example.app.service.UserEntityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    protected UserEntityService userEntityService;

    @GetMapping("/auth")
    private ResponseEntity<?> authUser(HttpServletRequest request) {
        try{
            userEntityService.authUser(request);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/signup")
    private ResponseEntity<?> addUser(@RequestBody UserEntityDto userEntityDto) {
        try{
            UserEntity userEntitySaved = userEntityService.saveUserEntity(userEntityDto);
            return new ResponseEntity<>(userEntitySaved, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    private ResponseEntity<?> loginUser(@RequestBody UserEntityDto userEntityDto, HttpServletResponse response) {
        try{
            userEntityService.loginUser(response, userEntityDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
