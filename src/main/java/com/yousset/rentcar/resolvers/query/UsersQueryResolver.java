package com.yousset.rentcar.resolvers.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yousset.rentcar.models.Users;
import com.yousset.rentcar.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsersQueryResolver implements GraphQLQueryResolver {

    @Autowired
    private UsersService usersService;

    public Iterable<Users> findAllUsers() {
        return usersService.findAllUsers();
    }

    public Users findUserById(Long id) {
        return usersService.findById(id).orElse(null);
    }

}
