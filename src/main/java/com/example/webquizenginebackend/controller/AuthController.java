package com.example.webquizenginebackend.controller;

import com.example.webquizenginebackend.payload.request.SignupRequest;
import com.example.webquizenginebackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    //регістрація користувача
    @PostMapping("/api/register")
    public ResponseEntity<Object> register(@Valid @RequestBody SignupRequest signupRequest){
        if (userService.findUserByUsername(signupRequest.getEmail()) != null){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.createUser(signupRequest),HttpStatus.OK);
    }
}
