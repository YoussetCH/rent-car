package com.yousset.rentcar.services;

import com.yousset.rentcar.controllers.exceptions.CustomException;
import com.yousset.rentcar.models.Cars;
import com.yousset.rentcar.models.Reserve;
import com.yousset.rentcar.models.Users;
import com.yousset.rentcar.repositories.CarsRepository;
import com.yousset.rentcar.repositories.ReserveRepository;
import com.yousset.rentcar.requests.CreateReserve;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("ALL")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReserveServiceTest {

    @Autowired
    ReserveService reserveService;
    @MockBean
    ReserveRepository reserveRepository;
    @MockBean
    CarsRepository carsRepository;
    @MockBean
    UsersService usersService;

    @Test
    public void whencreateReserveSuccesThenShouldReturnNewReserve() throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();

        CreateReserve createReserve = new CreateReserve();
        createReserve.setDtFrom(Date.from(localDateTime.plus(5L, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC)));
        createReserve.setDtTo(Date.from(localDateTime.plus(6L, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC)));
        createReserve.setLicensePlate("AA");

        Cars car = new Cars("AA", 1, 45.0);

        Reserve reserve = new Reserve();
        reserve.setCar(car);
        reserve.setDtFrom(createReserve.getDtFrom());
        reserve.setDtTo(createReserve.getDtTo());
        reserve.setPrice(45.0);
        reserve.setStatus(1);
        reserve.setUser(new Users(1L, 1));

        when(usersService.getPrincipal(any(Principal.class))).thenReturn(new Users(1L, 1));
        when(carsRepository.findById(anyString())).thenReturn(Optional.of(car));
        when(reserveRepository.existsByUserIdUserAndStatusIn(anyLong(), anyList())).thenReturn(false);
        when(reserveRepository.save(any(Reserve.class))).thenReturn(reserve);
        when(carsRepository.save(any(Cars.class))).thenReturn(car);

        Reserve result = reserveService.createReserve(new UserPrincipal() {
            @Override
            public String getName() {
                return null;
            }
        }, 1L, createReserve);

        assertNotNull(result);
        assertNotNull(result.getCar());
        assertEquals(result.getPrice(), reserve.getPrice());
    }

    @Test
    public void whencreateReserveFailDateThenShouldThrowException() throws IOException {
        LocalDateTime localDateTime = LocalDateTime.now();

        CreateReserve createReserve = new CreateReserve();
        createReserve.setDtFrom(Date.from(localDateTime.minus(1L, ChronoUnit.DAYS).toInstant(ZoneOffset.UTC)));
        createReserve.setDtTo(Date.from(localDateTime.plus(6L, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC)));
        createReserve.setLicensePlate("AA");

        Cars car = new Cars("AA", 1, 45.0);

        Reserve reserve = new Reserve();
        reserve.setCar(car);
        reserve.setDtFrom(createReserve.getDtFrom());
        reserve.setDtTo(createReserve.getDtTo());
        reserve.setPrice(45.0);
        reserve.setStatus(1);
        reserve.setUser(new Users(1L, 1));


        assertThrows(CustomException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Reserve result = reserveService.createReserve(new UserPrincipal() {
                    @Override
                    public String getName() {
                        return null;
                    }
                }, 1L, createReserve);
            }
        });

        verify(usersService, times(1)).getPrincipal(any(Principal.class));
    }

    //TODO Se que Faltan test para el coverage, pero realice un par de test para comprobar funcionalidad basica y demostracion de conocimiento.
}
