package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.infrastructue.out.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    Page<DishEntity> findByRestaurantIdAndCategoryId(RestaurantEntity restaurantEntity, CategoryEntity categoryEntity, Pageable pageable);
}
