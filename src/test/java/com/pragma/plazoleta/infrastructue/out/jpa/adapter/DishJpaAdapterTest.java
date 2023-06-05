package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IDishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    @Test
    void saveDish() {
        CategoryModel categoryModel = new CategoryModel(1L, "", "");
        CategoryModel categoryModelResult = new CategoryModel(1L, "Categoria1", "Categoria Descripción");
        DishModel dishModel = new DishModel(1L, "pasta", 1000, "plato de pasta", "https://pasta", categoryModel, null, true);
        DishEntity dishEntity = new DishEntity();
        DishModel expectedModel = new DishModel();

        when(categoryServicePort.findCategoryById(dishModel.getCategoryModel().getId())).thenReturn(categoryModelResult);
        when(dishEntityMapper.toDishEntity(dishModel)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);
        when(dishEntityMapper.toDishModel(dishEntity)).thenReturn(expectedModel);

        DishModel actualModel = dishJpaAdapter.saveDish(dishModel);

        assertEquals(expectedModel, actualModel);
        verify(categoryServicePort, times(1)).findCategoryById(dishModel.getCategoryModel().getId());
        verify(dishEntityMapper, times(1)).toDishEntity(dishModel);
        verify(dishRepository, times(1)).save(dishEntity);
        verify(dishEntityMapper, times(1)).toDishModel(dishEntity);
    }

    @Test
    void findDishById_Success() {
        Long dishId = 1L;
        DishEntity dishEntity = new DishEntity();
        DishModel expectedModel = new DishModel();

        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));
        when(dishEntityMapper.toDishModel(dishEntity)).thenReturn(expectedModel);

        DishModel actualModel = dishJpaAdapter.findDishById(dishId);

        assertEquals(expectedModel, actualModel);
        verify(dishRepository, times(1)).findById(dishId);
        verify(dishEntityMapper, times(1)).toDishModel(dishEntity);
    }

    @Test
    void findDishById_RequestException() {
        Long dishId = 1L;

        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        assertThrows(RequestException.class, () -> dishJpaAdapter.findDishById(dishId));

        verify(dishRepository, times(1)).findById(dishId);
        verify(dishEntityMapper, never()).toDishModel(any());
    }


}