package com.yousset.rentcar.services;

import com.yousset.rentcar.config.security.TokenProvider;
import com.yousset.rentcar.controllers.exceptions.CustomException;
import com.yousset.rentcar.models.Users;
import com.yousset.rentcar.repositories.UsersRepository;
import com.yousset.rentcar.requests.Register;
import com.yousset.rentcar.requests.UserLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@SuppressWarnings("ALL")
@Slf4j
@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> findById(Long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public String login(UserLogin userLogin) {
        Optional<Users> user = usersRepository.findByEmail(userLogin.getUser());
        if (user == null || !user.isPresent()) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (user.get().getStatus() != 1) {
            throw new CustomException("User Disable", HttpStatus.UNAUTHORIZED);
        }

        if (passwordEncoder.matches(userLogin.getPassword(), user.get().getPassword())) {
            return tokenProvider.createToken(userLogin.getUser());
        }

        throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Transactional
    public String register(Register register) {
        if (usersRepository.existsByEmail(register.getEmail())) {
            throw new CustomException("Username is already in use", HttpStatus.BAD_REQUEST);
        }

        Users user = new Users();
        user.setDtRegister(new Date());
        user.setEmail(register.getEmail());
        user.setName(register.getName());
        user.setLastName(register.getLastName());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(register.getRole());
        user.setStatus(1);

        user = usersRepository.save(user);

        return tokenProvider.createToken(null, user);
    }

    @Transactional
    public Users update(Register register, Users user) {
        user.setName(register.getName());
        user.setLastName(register.getLastName());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole(register.getRole());
        if (register.getStatus() != null) {
            user.setStatus(register.getStatus());
        }

        user = usersRepository.save(user);

        return user;
    }

    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username, Users users) throws UsernameNotFoundException {
        final Optional<Users> user = users == null ? usersRepository.findByEmail(username) : Optional.of(users);

        if (user == null || !user.isPresent()) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }

        return org.springframework.security.core.userdetails.User//
                .withUsername(user.get().getEmail())//
                .password(user.get().getPassword())//
                .authorities(Collections.singleton(new SimpleGrantedAuthority(user.get().getRole())))//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }

    public Users getPrincipal(Principal principal) {
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken p = (UsernamePasswordAuthenticationToken) principal;
            if (p.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
                return usersRepository.findByEmail(((org.springframework.security.core.userdetails.User) p.getPrincipal()).getUsername()).orElse(null);
            }
        }
        return null;
    }
}
