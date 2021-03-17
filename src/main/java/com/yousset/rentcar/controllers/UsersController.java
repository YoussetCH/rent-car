package com.yousset.rentcar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.yousset.rentcar.models.Users;
import com.yousset.rentcar.models.json_views.EntityView;
import com.yousset.rentcar.requests.Register;
import com.yousset.rentcar.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/users")
@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @GetMapping(value = "",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<Users>> findUsers() {
        return new ResponseEntity<>(
                usersService.findAllUsers(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{id}/reserves",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Complete.class)
    public ResponseEntity<Users> findUsersReserves(@PathVariable Long id) {
        Optional<Users> users = usersService.findById(id);

        if (users != null && users.isPresent()) {
            return new ResponseEntity<>(
                    users.get(),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping(value = "",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> register(@Valid @RequestBody Register body) {
        return new ResponseEntity<>(
                usersService.register(body),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Users> register(@PathVariable Long id, @Valid @RequestBody Register body) {
        Optional<Users> users = usersService.findById(id);

        if (users != null && users.isPresent()) {
            return new ResponseEntity<>(
                    usersService.update(body, users.get()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
