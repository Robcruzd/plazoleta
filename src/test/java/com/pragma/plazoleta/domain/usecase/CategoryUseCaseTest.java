package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    void findCategoryById() {
        Long categoryId = 1L;
        CategoryModel categoryModel = new CategoryModel(1L, "Categoria1", "Categoria Descripción");

        when(categoryPersistencePort.findCategoryById(categoryId)).thenReturn(categoryModel);

        CategoryModel categoryModelReturn = categoryUseCase.findCategoryById(categoryId);

        assertEquals(categoryModel, categoryModelReturn);
    }
}