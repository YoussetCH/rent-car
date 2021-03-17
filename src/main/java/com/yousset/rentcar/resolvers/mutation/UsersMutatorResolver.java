package com.yousset.rentcar.resolvers.mutation;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.yousset.rentcar.requests.Register;
import com.yousset.rentcar.requests.UserLogin;
import com.yousset.rentcar.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Component
public class UsersMutatorResolver implements GraphQLMutationResolver {

    @Autowired
    private UsersService usersService;
    @Autowired
    private Validator validator;

    public String login(String user, String password) {
        UserLogin userLogin = new UserLogin();
        userLogin.setUser(user);
        userLogin.setPassword(password);

        Set<ConstraintViolation<UserLogin>> constraintViolation = validator.validate(userLogin);

        if (!constraintViolation.isEmpty()) {
            throw new ConstraintViolationException(constraintViolation);
        }

        return usersService.login(userLogin);
    }

    public String register(String name, String lastName, String email, String password) {
        Register register = new Register();
        register.setName(name);
        register.setLastName(lastName);
        register.setEmail(email);
        register.setPassword(password);
        register.setStatus(1);
        register.setRole("USER");

        Set<ConstraintViolation<Register>> constraintViolation = validator.validate(register);
        if (!constraintViolation.isEmpty()) {
            throw new ConstraintViolationException(constraintViolation);
        }

        return usersService.register(register);
    }

}
