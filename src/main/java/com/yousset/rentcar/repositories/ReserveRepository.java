package com.yousset.rentcar.repositories;

import com.yousset.rentcar.models.Reserve;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReserveRepository extends PagingAndSortingRepository<Reserve, Long> {

    Iterable<Reserve> findByStatus(Integer status);

    Iterable<Reserve> findByUserIdUser(Long idUser);

    Iterable<Reserve> findByUserIdUserAndStatus(Long idUser, Integer status);

    boolean existsByUserIdUserAndStatusIn(Long idUser, List<Integer> status);

}
