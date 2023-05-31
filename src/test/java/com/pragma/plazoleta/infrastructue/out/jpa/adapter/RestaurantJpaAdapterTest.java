package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IRestaurantRepository;
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
class RestaurantJpaAdapterTest {

    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    @Test
    void saveRestaurant() {
        RestaurantModel restaurantModel = new RestaurantModel();
        RestaurantEntity restaurantEntity = new RestaurantEntity();

        when(restaurantEntityMapper.toEntity(restaurantModel)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);

        restaurantJpaAdapter.saveRestaurant(restaurantModel);

        verify(restaurantEntityMapper, times(1)).toEntity(restaurantModel);
        verify(restaurantRepository, times(1)).save(restaurantEntity);
    }

    @Test
    void findRestaurantById_Success() {
        Long restaurantId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        RestaurantModel expectedModel = new RestaurantModel();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));
        when(restaurantEntityMapper.toModel(restaurantEntity)).thenReturn(expectedModel);

        RestaurantModel actualModel = restaurantJpaAdapter.findRestaurantById(restaurantId);

        assertEquals(expectedModel, actualModel);
        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantEntityMapper, times(1)).toModel(restaurantEntity);
    }

    @Test
    void findRestaurantById_RequestException() {
        Long restaurantId = 1L;

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RequestException.class, () -> restaurantJpaAdapter.findRestaurantById(restaurantId));

        verify(restaurantRepository, times(1)).findById(restaurantId);
        verify(restaurantEntityMapper, never()).toModel(any());
    }
}