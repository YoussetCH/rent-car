package com.yousset.rentcar.services;

import com.yousset.rentcar.controllers.exceptions.CustomException;
import com.yousset.rentcar.models.Cars;
import com.yousset.rentcar.models.Reserve;
import com.yousset.rentcar.models.Users;
import com.yousset.rentcar.repositories.CarsRepository;
import com.yousset.rentcar.repositories.ReserveRepository;
import com.yousset.rentcar.requests.CreateReserve;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;

@SuppressWarnings("ALL")
@Slf4j
@Service
public class ReserveService {

    @Autowired
    ReserveRepository reserveRepository;
    @Autowired
    CarsRepository carsRepository;
    @Autowired
    UsersService usersService;

    public Iterable<Reserve> findAll() {
        return reserveRepository.findAll();
    }

    public Iterable<Reserve> findByStatus(Integer status) {
        return reserveRepository.findByStatus(status);
    }

    public Iterable<Reserve> findByUserIdUser(Long idUser) {
        return reserveRepository.findByUserIdUser(idUser);
    }

    public Iterable<Reserve> findByUserIdUserAAndStatus(Long idUser, Integer status) {
        return reserveRepository.findByUserIdUserAndStatus(idUser, status);
    }

    @Transactional
    public Reserve createReserve(Principal principal, Long idUser, CreateReserve createReserve) {
        Users user = usersService.getPrincipal(principal);
        return createReserve(user, idUser, createReserve);
    }

    @Transactional
    public Reserve createReserve(Users user, Long idUser, CreateReserve createReserve) {
        if (createReserve.getDtFrom().compareTo(new Date()) < 0) {
            throw new CustomException("Invalid Date From supplied", HttpStatus.BAD_REQUEST);
        }

        if (createReserve.getDtTo().compareTo(createReserve.getDtFrom()) <= 0) {
            throw new CustomException("Invalid Date To supplied", HttpStatus.BAD_REQUEST);
        }

        if (user == null || user.getStatus() != 1) {
            throw new CustomException("Invalid User supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (user.getIdUser() != idUser.longValue()) {
            throw new CustomException("Invalid User Request", HttpStatus.UNAUTHORIZED);
        }

        Cars car = carsRepository.findById(createReserve.getLicensePlate()).orElse(null);
        if (car == null) {
            throw new CustomException("Invalid Card supplied", HttpStatus.NOT_FOUND);
        }

        if (car.getStatus() != 1) {
            throw new CustomException("Card Unavailable", HttpStatus.BAD_REQUEST);
        }

        if (reserveRepository.existsByUserIdUserAndStatusIn(idUser, Arrays.asList(1, 2))) {
            throw new CustomException("The user has an active reservation.", HttpStatus.BAD_REQUEST);
        }

        Reserve reserve = new Reserve();
        reserve.setCar(car);
        reserve.setDtFrom(createReserve.getDtFrom());
        reserve.setDtTo(createReserve.getDtTo());
        reserve.setPrice(car.getPrice() != null && car.getPrice() > 0 ? car.getPrice() : car.getGama().getPrice());
        reserve.setStatus(1);
        reserve.setUser(user);

        reserve = reserveRepository.save(reserve);

        car.setStatus(2);
        carsRepository.save(car);

        return reserve;
    }

    @Transactional
    public Reserve cancelReserve(Principal principal, Long idReserve) {
        Users user = usersService.getPrincipal(principal);
        return cancelReserve(user, idReserve);
    }

    @Transactional
    public Reserve cancelReserve(Users user, Long idReserve) {
        if (user == null || user.getStatus() != 1) {
            throw new CustomException("Invalid User supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Reserve reserve = reserveRepository.findById(idReserve).orElse(null);
        if (reserve == null) {
            throw new CustomException("Invalid Reserve supplied", HttpStatus.NOT_FOUND);
        }

        if (reserve.getStatus() != 1) {
            throw new CustomException("Reservation cannot be canceled", HttpStatus.BAD_REQUEST);
        }

        if (reserve.getUser().getIdUser() != user.getIdUser().longValue()) {
            throw new CustomException("Invalid User Request", HttpStatus.UNAUTHORIZED);
        }

        Cars car = reserve.getCar();

        reserve.setStatus(0);
        reserve = reserveRepository.save(reserve);

        car.setStatus(1);
        carsRepository.save(car);

        return reserve;
    }

}
