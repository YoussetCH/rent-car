package com.yousset.rentcar.resolvers.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yousset.rentcar.config.security.TokenProvider;
import com.yousset.rentcar.models.Reserve;
import com.yousset.rentcar.resolvers.ResolverUtils;
import com.yousset.rentcar.services.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReserveQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private ReserveService reserveService;
    @Autowired
    private TokenProvider tokenProvider;

    public Iterable<Reserve> findAllReserve(String token) {
        ResolverUtils.getAuthentication(token, "ADMIN", tokenProvider);
        return reserveService.findAll();
    }

    public Iterable<Reserve> findAllReserveByStatus(String token, Integer status) {
        ResolverUtils.getAuthentication(token, "ADMIN", tokenProvider);
        return reserveService.findByStatus(status);
    }

    public Iterable<Reserve> findAllReserveByUser(String token, Long idUser) {
        ResolverUtils.getAuthentication(token, null, tokenProvider);
        return reserveService.findByUserIdUser(idUser);
    }

    public Iterable<Reserve> findAllReserveByUserAndStatus(String token, Long idUser, Integer status) {
        ResolverUtils.getAuthentication(token, null, tokenProvider);
        return reserveService.findByUserIdUserAAndStatus(idUser, status);
    }

}
