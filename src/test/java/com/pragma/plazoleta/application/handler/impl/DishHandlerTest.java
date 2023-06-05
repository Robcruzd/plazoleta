package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.CategoryRequestDto;
import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.DishListResponseDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IValidateOwnerRestaurant;
import com.pragma.plazoleta.application.mapper.IDishRequestMapper;
import com.pragma.plazoleta.application.mapper.IDishResponseMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantRequesMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DishHandlerTest {

    @Mock
    private IDishServicePort dishServicePort;
    @Mock
    private IDishRequestMapper dishRequestMapper;
    @Mock
    private IDishResponseMapper dishResponseMapper;
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IRestaurantRequesMapper restaurantRequesMapper;
    @Mock
    private IValidateOwnerRestaurant validateOwnerRestaurant;

    @InjectMocks
    private DishHandler dishHandler;

    @Test
    void saveDish() {
        Long ownerId = 1L;
        DishRequestDto dishRequestDto = new DishRequestDto("pasta", 10000, "plato de pasta", "https://pasta", 1L, 1L);

    }

    @Test
    void saveDish_Success() throws ApplicationException {
        DishRequestDto dishRequestDto = new DishRequestDto("pasta", 1000, "plato de pasta", "https://pasta", 1L, 1L);

        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(1L, "Categoria1", "Categoria Descripción");
        DishResponseDto dishResponseDto = new DishResponseDto("pasta", 1000, "plato de pasta", "https://pasta", categoryRequestDto, null, true);

        RestaurantModel restaurantModel = new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);

        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);

        CategoryModel categoryModel = new CategoryModel(1L, "Categoria1", "Categoria Descripción");
        DishModel dishModel = new DishModel(1L, "pasta", 1000, "plato de pasta", "https://pasta", categoryModel, restaurantModel, true);

        when(dishResponseMapper.toDishResDto(dishRequestDto)).thenReturn(dishResponseDto);
        when(restaurantServicePort.findRestaurantById(dishRequestDto.getRestaurantRequestDto())).thenReturn(restaurantModel);
        when(restaurantRequesMapper.toRestaurantDto(restaurantModel)).thenReturn(restaurantRequestDto);
        doNothing().when(validateOwnerRestaurant).validate(restaurantRequestDto, "token");
        when(dishResponseMapper.toDishModel(dishResponseDto)).thenReturn(dishModel);
        doNothing().when(dishServicePort).saveDish(dishModel);

        assertDoesNotThrow(() -> dishHandler.saveDish(dishRequestDto, "token"));

        verify(dishResponseMapper, times(1)).toDishResDto(dishRequestDto);
        verify(restaurantServicePort, times(1)).findRestaurantById(dishRequestDto.getRestaurantRequestDto());
        verify(restaurantRequesMapper, times(1)).toRestaurantDto(restaurantModel);
        verify(validateOwnerRestaurant, times(1)).validate(restaurantRequestDto, "token");
        verify(dishResponseMapper, times(1)).toDishModel(dishResponseDto);
        verify(dishServicePort, times(1)).saveDish(dishModel);
    }

    @Test
    void saveDish_ValidationException() {
        DishRequestDto dishRequestDto = new DishRequestDto("pasta", 1000, "plato de pasta", "https://pasta", 1L, 1L);
        RestaurantModel restaurantModel = new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);
        DishResponseDto dishResponseDto = new DishResponseDto("pasta", 1000, "plato de pasta", "https://pasta", null, null, true);
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);
        DomainException domainException = new DomainException("La validación falló");

        when(restaurantServicePort.findRestaurantById(any())).thenReturn(restaurantModel);
        when(dishResponseMapper.toDishResDto(any())).thenReturn(dishResponseDto);
        when(restaurantRequesMapper.toRestaurantDto(any())).thenReturn(restaurantRequestDto);
        doThrow(domainException).when(validateOwnerRestaurant).validate(any(), any());

        assertThrows(ApplicationException.class, () -> dishHandler.saveDish(dishRequestDto, "token"));

        verify(dishResponseMapper, never()).toDishModel(any());
        verify(dishServicePort, never()).saveDish(any());
    }

    @Test
    void updateDish() {
        Long ownerId = 1L;
        DishUpdateRequestDto dishUpdateRequestDto = new DishUpdateRequestDto(1L, 1000, "plato de pasta");
        dishUpdateRequestDto.setDescription("Nueva descripción");
        dishUpdateRequestDto.setPrecio(500); // Precio actualizado

        CategoryModel categoryModel = new CategoryModel(1L, "Categoria1", "Categoria Descripción");
        RestaurantModel restaurantModel = new RestaurantModel(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);
        DishModel dishModel = new DishModel(1L, "pasta", 1000, "plato de pasta", "https://pasta", categoryModel, restaurantModel, true);
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto(1L, "Restaurante1", 123456L, "calle 123", "09876543", "https://urlLogo", 1L);

        when(dishServicePort.findDishById(any())).thenReturn(dishModel);
        when(restaurantServicePort.findRestaurantById(any())).thenReturn(restaurantModel);
        when(restaurantRequesMapper.toRestaurantDto(any())).thenReturn(restaurantRequestDto);
        doNothing().when(validateOwnerRestaurant).validate(restaurantRequestDto, "token");

        dishHandler.updateDish(dishUpdateRequestDto, "token");

        verify(dishServicePort, times(1)).saveDish(dishModel);
    }

    @Test
    void findDishById() {
        long dishId = 1L;

        DishModel dishModel = new DishModel(1L, "pasta", 1000, "plato de pasta", "https://pasta", null, null, true);
        DishResponseDto dishResponseDto = new DishResponseDto("pasta", 1000, "plato de pasta", "https://pasta", null, null, true);

        when(dishServicePort.findDishById(dishId)).thenReturn(dishModel);
        when(dishResponseMapper.toDishResDtoFromModel(dishModel)).thenReturn(dishResponseDto);

        DishResponseDto dishResponseDtoReturn = dishHandler.findDishById(dishId);

        assertEquals(dishResponseDto, dishResponseDtoReturn);
    }

    @Test
    void listDishes_Success() {
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int page = 1;
        int size = 10;

        List<DishListResponseDto> dishListResponseDtos= new ArrayList<>();
        dishListResponseDtos.add(new DishListResponseDto("pasta", 1000, "descripción", "url", true));
        dishListResponseDtos.add(new DishListResponseDto("pasta2", 1000, "descripción", "url", true));

        List<DishModel> dishModels = new ArrayList<>();
        dishModels.add(new DishModel(1L, "pasta", 1000, "plato de pasta", "https://pasta", null, null, true));
        dishModels.add(new DishModel(2L, "pasta", 1000, "plato de pasta2", "https://pasta", null, null, true));

        when(dishServicePort.findDishesByRestaurantIdAndCategoryId(restaurantId, categoryId, page, size))
                .thenReturn(dishModels);
        when(dishResponseMapper.toDishListDto(dishModels)).thenReturn(dishListResponseDtos);

        List<DishListResponseDto> result = dishHandler.listDishes(restaurantId, categoryId, page, size);

        assertEquals(dishListResponseDtos.size(), result.size());
        assertEquals(dishListResponseDtos.get(0).getName(), result.get(0).getName());
        assertEquals(dishListResponseDtos.get(0).getPrice(), result.get(0).getPrice());
        assertEquals(dishListResponseDtos.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(dishListResponseDtos.get(1).getName(), result.get(1).getName());
        assertEquals(dishListResponseDtos.get(1).getPrice(), result.get(1).getPrice());
        assertEquals(dishListResponseDtos.get(1).getDescription(), result.get(1).getDescription());

        verify(dishServicePort).findDishesByRestaurantIdAndCategoryId(restaurantId, categoryId, page, size);
        verifyNoMoreInteractions(dishServicePort);
    }

    @Test
    void listDishes_DomainException() {
        Long restaurantId = 1L;
        Long categoryId = 2L;
        int page = 1;
        int size = 10;

        DomainException exception = new DomainException("Error solicitando platos");

        when(dishServicePort.findDishesByRestaurantIdAndCategoryId(restaurantId, categoryId, page, size))
                .thenThrow(exception);

        assertThrows(ApplicationException.class, () -> dishHandler.listDishes(restaurantId, categoryId, page, size));

        verify(dishServicePort).findDishesByRestaurantIdAndCategoryId(restaurantId, categoryId, page, size);
        verifyNoMoreInteractions(dishServicePort);
    }
}