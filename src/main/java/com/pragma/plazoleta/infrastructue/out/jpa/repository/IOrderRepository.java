package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByCustomerIdAndStatusId(Long customerId, Long statusId);

    Optional<OrderEntity> findFirstByCustomerIdOrderByIdDesc(Long customerId);
}