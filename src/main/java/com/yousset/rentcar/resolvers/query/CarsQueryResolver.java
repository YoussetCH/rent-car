package com.yousset.rentcar.resolvers.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yousset.rentcar.models.Cars;
import com.yousset.rentcar.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarsQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private CarsService carsService;

    public Iterable<Cars> findAllCars() {
        return carsService.findAll();
    }

    public Iterable<Cars> findAllCarsAvailable() {
        return carsService.findByStatus(1);
    }

    public Iterable<Cars> findCardByGama(String gama) {
        return carsService.findByGama(gama);
    }

    public Iterable<Cars> findCardAvailableByGama(String gama) {
        return carsService.findByGama(1, gama);
    }

}
