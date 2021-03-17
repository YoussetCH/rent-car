package com.yousset.rentcar.resolvers.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.yousset.rentcar.config.security.TokenProvider;
import com.yousset.rentcar.models.Reserve;
import com.yousset.rentcar.requests.CreateReserve;
import com.yousset.rentcar.resolvers.ResolverUtils;
import com.yousset.rentcar.services.ReserveService;
import com.yousset.rentcar.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ReserveMutatorResolver implements GraphQLMutationResolver {

    @Autowired
    private ReserveService reserveService;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private Validator validator;
    @Autowired
    private UsersService usersService;

    public Reserve createReserve(String token, Long idUser, String licensePlate, String dtFrom, String dtTo) {
        org.springframework.security.core.userdetails.UserDetails userDetails = ResolverUtils.getAuthentication(token, null, tokenProvider);

        CreateReserve createReserve = new CreateReserve();
        createReserve.setLicensePlate(licensePlate);
        createReserve.setDtFrom(ResolverUtils.parseDate(dtFrom));
        createReserve.setDtTo(ResolverUtils.parseDate(dtTo));

        Set<ConstraintViolation<CreateReserve>> constraintViolation = validator.validate(createReserve);
        if (!constraintViolation.isEmpty()) {
            throw new ConstraintViolationException(constraintViolation);
        }

        return reserveService.createReserve(usersService.findByEmail(userDetails.getUsername()).orElse(null), idUser, createReserve);
    }

    public Reserve cancelReserve(String token, Long idReserve) {
        org.springframework.security.core.userdetails.UserDetails userDetails = ResolverUtils.getAuthentication(token, null, tokenProvider);
        return reserveService.cancelReserve(usersService.findByEmail(userDetails.getUsername()).orElse(null), idReserve);
    }

}
