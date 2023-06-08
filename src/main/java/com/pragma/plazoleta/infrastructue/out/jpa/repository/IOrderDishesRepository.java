package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderDishesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDishesRepository extends JpaRepository<OrderDishesEntity, Long> {
}
