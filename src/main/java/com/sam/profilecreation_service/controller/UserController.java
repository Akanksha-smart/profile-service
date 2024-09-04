package com.sam.profilecreation_service.controller;


import com.sam.profilecreation_service.entity.UserEntity;
import com.sam.profilecreation_service.service.UserService;
import com.sam.profilecreation_service.service.impl.UserServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody UserEntity userEntity ) {
        try {
            System.out.println("In post"+userEntity.toString());
            UserEntity registeredUser = userService.signup(userEntity);
            System.out.println("After"+userEntity.toString());
//            return new ResponseEntity<>(registeredUser, HttpStatus.OK);
            return "Created";
        } catch (Exception e) {
            System.out.println(userEntity.toString());
//            return new ResponseEntity<>(null);
            return "Error";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> loginUser(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            UserEntity loggedInUser = userService.login(username, password);
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
}
