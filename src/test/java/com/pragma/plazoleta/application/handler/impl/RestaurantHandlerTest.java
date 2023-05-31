package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.mapper.IRestaurantRequesMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IRestaurantRequesMapper restaurantRequesMapper;

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    private RestaurantModel restaurantModel;
    private RestaurantRequestDto restaurantRequestDto;

    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);
        restaurantRequestDto = new RestaurantRequestDto(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);
    }

    @Test
    void saveRestaurant() {

        when(restaurantRequesMapper.toRestaurantModel(restaurantRequestDto)).thenReturn(restaurantModel);

        restaurantHandler.saveRestaurant(restaurantRequestDto);

        verify(restaurantServicePort, times(1)).saveRestaurant(restaurantModel);
    }

    @Test
    void findRestaurantById() {
        Long restaurantId = 1L;

        when(restaurantServicePort.findRestaurantById(restaurantId)).thenReturn(restaurantModel);
        when(restaurantRequesMapper.toRestaurantDto(restaurantModel)).thenReturn(restaurantRequestDto);

        RestaurantRequestDto restaurantRequestDtoReturn = restaurantHandler.findRestaurantById(restaurantId);

        assertEquals(restaurantRequestDto, restaurantRequestDtoReturn);
    }
}