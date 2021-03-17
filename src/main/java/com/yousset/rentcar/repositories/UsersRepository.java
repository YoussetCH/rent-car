package com.yousset.rentcar.repositories;

import com.yousset.rentcar.models.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String username);

}
