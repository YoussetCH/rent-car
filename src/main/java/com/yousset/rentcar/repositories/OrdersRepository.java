package com.yousset.rentcar.repositories;

import com.yousset.rentcar.models.Orders;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends PagingAndSortingRepository<Orders, Long> {

    Iterable<Orders> findByStatus(Integer status);

    Iterable<Orders> findByReserveUserIdUser(Long idUser);

    Iterable<Orders> findByReserveUserIdUserAndStatus(Long idUser, Integer status);

    boolean existsByReserveUserIdUserAndStatusIn(Long idUser, List<Integer> status);

}
