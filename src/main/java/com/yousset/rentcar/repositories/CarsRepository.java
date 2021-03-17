package com.yousset.rentcar.repositories;

import com.yousset.rentcar.models.Cars;
import com.yousset.rentcar.models.Users;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarsRepository extends PagingAndSortingRepository<Cars, String> {

    Iterable<Cars> findByStatus(Integer status);

    Iterable<Cars> findByGama_Name(String name);

    Iterable<Cars> findByStatusAndGama_Name(Integer status, String name);

}
