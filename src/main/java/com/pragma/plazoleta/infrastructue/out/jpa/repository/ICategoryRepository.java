package com.pragma.plazoleta.infrastructue.out.jpa.repository;

import com.pragma.plazoleta.infrastructue.out.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
