package com.yousset.rentcar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.yousset.rentcar.models.Orders;
import com.yousset.rentcar.models.json_views.EntityView;
import com.yousset.rentcar.models.json_views.UsersEntityView;
import com.yousset.rentcar.requests.CloseOrder;
import com.yousset.rentcar.requests.CreateOrder;
import com.yousset.rentcar.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/orders")
@RestController
public class OrdersController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(UsersEntityView.Complete.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<Orders>> findAll() {
        return new ResponseEntity<>(
                orderService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/status/{status}",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(UsersEntityView.Complete.class)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Iterable<Orders>> findAllByStatus(@PathVariable Integer status) {
        return new ResponseEntity<>(
                orderService.findByStatus(status),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/user/{idUser}",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Orders>> findAllByUser(@PathVariable Long idUser) {
        return new ResponseEntity<>(
                orderService.findByUserIdUser(idUser),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/user/{idUser}/status/{status}",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Orders>> findAllByUser(@PathVariable Long idUser, @PathVariable Integer status) {
        return new ResponseEntity<>(
                orderService.findByUserIdUserAAndStatus(idUser, status),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/user/{idUser}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Orders> createOrder(Principal principal, @PathVariable Long idUser, @Valid @RequestBody CreateOrder body) {
        return new ResponseEntity<>(
                orderService.createOrder(principal, idUser, body),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Orders> closeOrder(Principal principal, @PathVariable Long id, @Valid @RequestBody CloseOrder closeOrder) {
        return new ResponseEntity<>(
                orderService.closeOrder(principal, id, closeOrder),
                HttpStatus.OK
        );
    }
}
