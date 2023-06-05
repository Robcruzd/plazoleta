package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.RestaurantListResponseDto;
import com.pragma.plazoleta.application.mapper.IRestaurantRequesMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantResponseMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

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
    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;
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

    @Test
    void testListRestaurants() {
        int page = 1;
        int size = 10;

        List<RestaurantModel> mockedRestaurantModels = Arrays.asList(
                new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L),
                new RestaurantModel(2L, "Restaurante2", 123456L, "calle 123", "09876543", "https://urlLogo", 1L)
        );
        List<RestaurantListResponseDto> expectedRestaurantDtos = Arrays.asList(
                new RestaurantListResponseDto("Restaurante1", "https://urlLogo"),
                new RestaurantListResponseDto("Restaurante2", "https://urlLogo")
        );

        when(restaurantServicePort.findAllRestaurants(page, size)).thenReturn(mockedRestaurantModels);

        when(restaurantResponseMapper.toRestaurantListDto(mockedRestaurantModels)).thenReturn(expectedRestaurantDtos);

        List<RestaurantListResponseDto> actualRestaurantDtos = restaurantHandler.listRestaurants(page, size);

        assertEquals(expectedRestaurantDtos, actualRestaurantDtos);
    }
}