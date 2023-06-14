package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestaurantEmployeeRepository extends JpaRepository<RestaurantEmployeeEntity, Long> {

    Optional<RestaurantEmployeeEntity> findByEmployeeId(Long employeeId);
}
