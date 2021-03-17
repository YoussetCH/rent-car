package com.yousset.rentcar.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.yousset.rentcar.models.Cars;
import com.yousset.rentcar.models.json_views.EntityView;
import com.yousset.rentcar.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(value = "/api/cars")
@RestController
public class CarsController {

    @Autowired
    CarsService carsService;

    @GetMapping(value = "",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Cars>> findAll() {
        return new ResponseEntity<>(
                carsService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/available",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Cars>> findAllAvailable() {
        return new ResponseEntity<>(
                carsService.findByStatus(1),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/gama/{gama}",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Cars>> findByGama(@PathVariable String gama) {
        return new ResponseEntity<>(
                carsService.findByGama(gama),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/gama/{gama}/available",
            produces = APPLICATION_JSON_VALUE)
    @JsonView(EntityView.Basic.class)
    public ResponseEntity<Iterable<Cars>> findAllAvailable(@PathVariable String gama) {
        return new ResponseEntity<>(
                carsService.findByGama(1, gama),
                HttpStatus.OK
        );
    }
}
