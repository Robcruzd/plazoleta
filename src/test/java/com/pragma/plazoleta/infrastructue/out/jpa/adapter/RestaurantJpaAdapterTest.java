package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IRestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void findAllRestaurants_Success() {
        // Arrange
        int page = 1;
        int size = 10;
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        List<RestaurantEntity> restaurantEntities = new ArrayList<>();
        restaurantEntities.add(new RestaurantEntity());
        restaurantEntities.add(new RestaurantEntity());

        Page<RestaurantEntity> pagedRestaurantEntities = new PageImpl<>(restaurantEntities, pageable, restaurantEntities.size());

        List<RestaurantModel> expectedRestaurantModels = new ArrayList<>();
        expectedRestaurantModels.add(new RestaurantModel());
        expectedRestaurantModels.add(new RestaurantModel());

        when(restaurantRepository.findAll(pageable)).thenReturn(pagedRestaurantEntities);
        when(restaurantEntityMapper.toRestaurantsModel(pagedRestaurantEntities)).thenReturn(expectedRestaurantModels);

        List<RestaurantModel> actualRestaurantModels = restaurantJpaAdapter.findAllRestaurants(page, size);

        assertEquals(expectedRestaurantModels.size(), actualRestaurantModels.size());
    }

    @Test
    void findAllRestaurants_Error() {
        int page = 1;
        int size = 10;
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        RequestException expectedException = new RequestException("Error", HttpStatus.NOT_FOUND);

        when(restaurantRepository.findAll(pageable)).thenThrow(expectedException);

        assertThrows(RequestException.class, () -> {
            restaurantJpaAdapter.findAllRestaurants(page, size);
        });
    }
}