package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStatusOrderRepository extends JpaRepository<StatusOrderEntity, Integer> {
}
