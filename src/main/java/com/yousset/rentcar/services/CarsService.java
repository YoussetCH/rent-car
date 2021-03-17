package com.yousset.rentcar.services;

import com.yousset.rentcar.models.Cars;
import com.yousset.rentcar.repositories.CarsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Slf4j
@Service
public class CarsService {

    @Autowired
    CarsRepository carsRepository;

    public Iterable<Cars> findAll() {
        return carsRepository.findAll();
    }

    public Iterable<Cars> findByStatus(Integer status) {
        return carsRepository.findByStatus(status);
    }

    public Iterable<Cars> findByGama(String gama) {
        return carsRepository.findByGama_Name(gama);
    }

    public Iterable<Cars> findByGama(Integer status, String gama) {
        return carsRepository.findByStatusAndGama_Name(status, gama);
    }

}
