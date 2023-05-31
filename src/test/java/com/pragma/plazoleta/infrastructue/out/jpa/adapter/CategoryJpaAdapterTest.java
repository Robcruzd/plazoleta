package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @Test
    void findCategoryById_ExistingCategory() {
        Long categoryId = 1L;
        CategoryEntity categoryEntity = new CategoryEntity();
        CategoryModel expectedModel = new CategoryModel();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        when(categoryEntityMapper.toCategoryModel(categoryEntity)).thenReturn(expectedModel);

        CategoryModel actualModel = categoryJpaAdapter.findCategoryById(categoryId);

        assertEquals(expectedModel, actualModel);
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryEntityMapper, times(1)).toCategoryModel(categoryEntity);
    }

    @Test
    void findCategoryById_NonExistingCategory() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(RequestException.class, () -> categoryJpaAdapter.findCategoryById(categoryId));

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryEntityMapper, never()).toCategoryModel(any());
    }
}