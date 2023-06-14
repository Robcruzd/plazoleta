package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.domain.model.OrderDishesModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderDishesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDishesRepository extends JpaRepository<OrderDishesEntity, Long> {

    List<OrderDishesEntity> findAllByOrderId(Long orderId);
}
