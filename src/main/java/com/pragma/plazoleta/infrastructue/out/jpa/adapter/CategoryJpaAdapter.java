package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.domain.spi.ICategoryPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.ICategoryRepository;
import org.springframework.http.HttpStatus;

public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    public CategoryJpaAdapter(ICategoryRepository categoryRepository, ICategoryEntityMapper categoryEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public CategoryModel findCategoryById(Long categoryId) {
        CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId).orElseThrow(() -> new RequestException("Categoria no encontrada", HttpStatus.NOT_FOUND));
        return categoryEntityMapper.toCategoryModel(categoryEntity);
    }
}
