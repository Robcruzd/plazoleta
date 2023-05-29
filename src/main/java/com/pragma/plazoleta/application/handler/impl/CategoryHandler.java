package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.CategoryRequestDto;
import com.pragma.plazoleta.application.handler.ICategoryHandler;
import com.pragma.plazoleta.application.mapper.ICategoryRequestMapper;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.model.CategoryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryRequestMapper categoryRequestMapper;

    @Override
    public CategoryRequestDto findCategoryById(Long categoryId) {
        CategoryModel categoryModel = this.categoryServicePort.findCategoryById(categoryId);
        return this.categoryRequestMapper.toCategoryDto(categoryModel);
    }
}
