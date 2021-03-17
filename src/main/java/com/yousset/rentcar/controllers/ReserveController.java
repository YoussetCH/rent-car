package com.yousset.rentcar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.yousset.rentcar.models.Reserve;
import com.yousset.rentcar.models.json_views.EntityView;
import com.yousset.rentcar.models.json_views.UsersEntityView;
import com.yousset.rentcar.requests.CreateReserve;
import com.yousset.rentcar.services.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/reserve")
@RestController
public class ReserveController {

    @Autowired
    ReserveService reserveService;

    @GetMapping(value = "",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(UsersEntityView.Complete.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<Reserve>> findAll() {
        return new ResponseEntity<>(
                reserveService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/status/{status}",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(UsersEntityView.Complete.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<Reserve>> findAllByStatus(@PathVariable Integer status) {
        return new ResponseEntity<>(
                reserveService.findByStatus(status),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/user/{idUser}",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Reserve>> findAllByUser(@PathVariable Long idUser) {
        return new ResponseEntity<>(
                reserveService.findByUserIdUser(idUser),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/user/{idUser}/status/{status}",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Reserve>> findAllByUser(@PathVariable Long idUser, @PathVariable Integer status) {
        return new ResponseEntity<>(
                reserveService.findByUserIdUserAAndStatus(idUser, status),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/user/{idUser}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Reserve> createReserve(Principal principal, @PathVariable Long idUser, @Valid @RequestBody CreateReserve body) {
        return new ResponseEntity<>(
                reserveService.createReserve(principal, idUser, body),
                HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Reserve> cancelReserve(Principal principal, @PathVariable Long id) {
        return new ResponseEntity<>(
                reserveService.cancelReserve(principal, id),
                HttpStatus.OK
        );
    }
}
