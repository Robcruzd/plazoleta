package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.CategoryRequestDto;
import com.pragma.plazoleta.application.mapper.ICategoryRequestMapper;
import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.model.CategoryModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    @Test
    void findCategoryById() {
        Long categoryId = 1L;
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(1L, "Categoria1", "Categoria Descripción");

        CategoryModel categoryModel = new CategoryModel(1L, "Categoria1", "Categoria Descripción");
        when(categoryServicePort.findCategoryById(categoryId)).thenReturn(categoryModel);
        when(categoryRequestMapper.toCategoryDto(categoryModel)).thenReturn(categoryRequestDto);

        CategoryRequestDto categoryRequestDtoReturn = categoryHandler.findCategoryById(categoryId);

        assertAll("Se prueba encontrar categoría",
                () -> assertEquals(categoryRequestDto.getId(), categoryRequestDtoReturn.getId()),
                () -> assertEquals(categoryRequestDto.getName(), categoryRequestDtoReturn.getName()),
                () -> assertEquals(categoryRequestDto.getDescription(), categoryRequestDtoReturn.getDescription()));
    }
}