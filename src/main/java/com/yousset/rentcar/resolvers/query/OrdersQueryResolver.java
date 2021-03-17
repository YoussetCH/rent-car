package com.yousset.rentcar.resolvers.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yousset.rentcar.config.security.TokenProvider;
import com.yousset.rentcar.models.Orders;
import com.yousset.rentcar.models.Reserve;
import com.yousset.rentcar.resolvers.ResolverUtils;
import com.yousset.rentcar.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrdersQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TokenProvider tokenProvider;

    public Iterable<Orders> findAllOrders(String token) {
        ResolverUtils.getAuthentication(token, "ADMIN", tokenProvider);
        return orderService.findAll();
    }

    public Iterable<Orders> findAllOrdersByStatus(String token, Integer status) {
        ResolverUtils.getAuthentication(token, "ADMIN", tokenProvider);
        return orderService.findByStatus(status);
    }

    public Iterable<Orders> findAllOrdersByUser(String token, Long idUser) {
        ResolverUtils.getAuthentication(token, null, tokenProvider);
        return orderService.findByUserIdUser(idUser);
    }

    public Iterable<Orders> findAllOrdersByUserAndStatus(String token, Long idUser, Integer status) {
        ResolverUtils.getAuthentication(token, null, tokenProvider);
        return orderService.findByUserIdUserAAndStatus(idUser, status);
    }

}
