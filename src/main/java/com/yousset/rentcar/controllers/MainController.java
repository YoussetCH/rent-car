package com.yousset.rentcar.controllers;

import com.yousset.rentcar.requests.Register;
import com.yousset.rentcar.requests.UserLogin;
import com.yousset.rentcar.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MainController {

    public static final String ENDPOINT = "/health-check";

    @Autowired
    UsersService usersService;

    @GetMapping(ENDPOINT)
    public String healthcheck() {
        return "OK";
    }

    @PostMapping(value = "/login",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@Valid @RequestBody UserLogin body) {
        return new ResponseEntity<>(
                usersService.login(body),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/register",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Valid @RequestBody Register body) {
        body.setRole("USER");
        return new ResponseEntity<>(
                usersService.register(body),
                HttpStatus.OK
        );
    }
}
