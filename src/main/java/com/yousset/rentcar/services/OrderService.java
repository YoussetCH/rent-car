package com.yousset.rentcar.services;

import com.yousset.rentcar.controllers.exceptions.CustomException;
import com.yousset.rentcar.models.Cars;
import com.yousset.rentcar.models.Orders;
import com.yousset.rentcar.models.Reserve;
import com.yousset.rentcar.models.Users;
import com.yousset.rentcar.repositories.CarsRepository;
import com.yousset.rentcar.repositories.OrdersRepository;
import com.yousset.rentcar.repositories.ReserveRepository;
import com.yousset.rentcar.requests.CloseOrder;
import com.yousset.rentcar.requests.CreateOrder;
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
public class OrderService {

    @Autowired
    ReserveRepository reserveRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    CarsRepository carsRepository;
    @Autowired
    UsersService usersService;

    public Iterable<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Iterable<Orders> findByStatus(Integer status) {
        return ordersRepository.findByStatus(status);
    }

    public Iterable<Orders> findByUserIdUser(Long idUser) {
        return ordersRepository.findByReserveUserIdUser(idUser);
    }

    public Iterable<Orders> findByUserIdUserAAndStatus(Long idUser, Integer status) {
        return ordersRepository.findByReserveUserIdUserAndStatus(idUser, status);
    }

    @Transactional
    public Orders createOrder(Principal principal, Long idUser, CreateOrder createOrder) {
        Users user = usersService.getPrincipal(principal);
        return createOrder(user, idUser, createOrder);
    }

    @Transactional
    public Orders createOrder(Users user, Long idUser, CreateOrder createOrder) {

        if (createOrder.getDtPickUp().compareTo(new Date()) < 0) {
            throw new CustomException("Invalid Date PickUp supplied", HttpStatus.BAD_REQUEST);
        }

        if (user == null || user.getStatus() != 1) {
            throw new CustomException("Invalid User supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Reserve reserve = reserveRepository.findById(createOrder.getReserve()).orElse(null);
        if (reserve == null) {
            throw new CustomException("Invalid Reserve supplied", HttpStatus.NOT_FOUND);
        }

        if (reserve.getStatus() != 1) {
            throw new CustomException("Invalid Reserve Status", HttpStatus.BAD_REQUEST);
        }

        Cars car = reserve.getCar();

        if (ordersRepository.existsByReserveUserIdUserAndStatusIn(idUser, Arrays.asList(1))) {
            throw new CustomException("The user has an active order.", HttpStatus.BAD_REQUEST);
        }

        Orders order = new Orders();
        order.setDtPickUp(createOrder.getDtPickUp());
        order.setPlacePickUp(createOrder.getPlacePickUp());
        order.setPrice(createOrder.getPrice() != null ? createOrder.getPrice() : reserve.getPrice());
        order.setRecharge(0.0);
        order.setStatus(1);
        order.setReserve(reserve);

        order = ordersRepository.save(order);

        reserve.setStatus(2);
        reserveRepository.save(reserve);

        car.setStatus(3);
        carsRepository.save(car);

        return order;
    }

    @Transactional
    public Orders closeOrder(Principal principal, Long idOrder, CloseOrder closeOrder) {
        Users user = usersService.getPrincipal(principal);
        return closeOrder(user, idOrder, closeOrder);
    }

    @Transactional
    public Orders closeOrder(Users user, Long idOrder, CloseOrder closeOrder) {
        if (user == null || user.getStatus() != 1) {
            throw new CustomException("Invalid User supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Orders orders = ordersRepository.findById(idOrder).orElse(null);
        if (orders == null) {
            throw new CustomException("Invalid Order supplied", HttpStatus.NOT_FOUND);
        }

        if (orders.getStatus() != 1) {
            throw new CustomException("Orders cannot be close", HttpStatus.BAD_REQUEST);
        }

        Reserve reserve = orders.getReserve();
        Cars car = reserve.getCar();

        orders.setRecharge(closeOrder.getRecharge());
        orders.setDtGiveUp(closeOrder.getDtGiveUp());
        orders.setPlaceGiveUp(closeOrder.getPlaceGiveUp());
        orders.setStatus(2);
        orders = ordersRepository.save(orders);

        reserve.setStatus(3);
        reserveRepository.save(reserve);

        car.setStatus(1);
        carsRepository.save(car);

        return orders;
    }

}
