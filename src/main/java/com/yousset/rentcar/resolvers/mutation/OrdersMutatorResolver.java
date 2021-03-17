package com.yousset.rentcar.resolvers.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.yousset.rentcar.config.security.TokenProvider;
import com.yousset.rentcar.models.Orders;
import com.yousset.rentcar.requests.CloseOrder;
import com.yousset.rentcar.requests.CreateOrder;
import com.yousset.rentcar.resolvers.ResolverUtils;
import com.yousset.rentcar.services.OrderService;
import com.yousset.rentcar.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class OrdersMutatorResolver implements GraphQLMutationResolver {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private Validator validator;
    @Autowired
    private UsersService usersService;

    public Orders createOrder(String token, Long idUser, Double price, Double recharge, String dtPickUp, String placePickUp, Long reserve) {
        org.springframework.security.core.userdetails.UserDetails userDetails = ResolverUtils.getAuthentication(token, "ADMIN", tokenProvider);

        CreateOrder createOrder = new CreateOrder();
        if (!StringUtils.isEmpty(dtPickUp)) {
            createOrder.setDtPickUp(ResolverUtils.parseDate(dtPickUp));
        }
        createOrder.setPlacePickUp(placePickUp);
        createOrder.setPrice(price);
        createOrder.setRecharge(recharge);
        createOrder.setReserve(reserve);

        Set<ConstraintViolation<CreateOrder>> constraintViolation = validator.validate(createOrder);
        if (!constraintViolation.isEmpty()) {
            throw new ConstraintViolationException(constraintViolation);
        }

        return orderService.createOrder(usersService.findByEmail(userDetails.getUsername()).orElse(null), idUser, createOrder);
    }

    public Orders closeOrder(String token, Long idOrder, Double recharge, String dtGiveUp, String placeGiveUp) {
        org.springframework.security.core.userdetails.UserDetails userDetails = ResolverUtils.getAuthentication(token, "ADMIN", tokenProvider);

        CloseOrder closeOrder = new CloseOrder();
        if (!StringUtils.isEmpty(dtGiveUp)) {
            closeOrder.setDtGiveUp(ResolverUtils.parseDate(dtGiveUp));
        }
        closeOrder.setPlaceGiveUp(placeGiveUp);
        closeOrder.setRecharge(recharge);

        Set<ConstraintViolation<CloseOrder>> constraintViolation = validator.validate(closeOrder);
        if (!constraintViolation.isEmpty()) {
            throw new ConstraintViolationException(constraintViolation);
        }

        return orderService.closeOrder(usersService.findByEmail(userDetails.getUsername()).orElse(null), idOrder, closeOrder);
    }

}
