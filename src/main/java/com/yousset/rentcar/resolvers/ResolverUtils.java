package com.yousset.rentcar.resolvers;

import com.yousset.rentcar.RentCarStartApplication;
import com.yousset.rentcar.config.security.TokenProvider;
import com.yousset.rentcar.controllers.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResolverUtils {

    public static Date parseDate(String parse) {
        try {
            return new SimpleDateFormat(RentCarStartApplication.DEFAULT_FORMAT_DATE_GRAPH).parse(parse);
        } catch (Exception e) {
            throw new CustomException("Invalid format Date", HttpStatus.BAD_REQUEST);
        }
    }

    public static org.springframework.security.core.userdetails.UserDetails getAuthentication(String token, String role, TokenProvider tokenProvider) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) tokenProvider.getAuthentication(token);
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
            org.springframework.security.core.userdetails.UserDetails userDetails = (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();
            if (!StringUtils.isEmpty(role) && userDetails.getAuthorities().stream().noneMatch(auth -> auth.getAuthority().equalsIgnoreCase(role))) {
                throw new UsernameNotFoundException("Invalid privileges");
            }

            return userDetails;
        }

        throw new UsernameNotFoundException("Invalid User");
    }
}
