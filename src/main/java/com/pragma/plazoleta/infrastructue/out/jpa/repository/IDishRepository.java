package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
}
