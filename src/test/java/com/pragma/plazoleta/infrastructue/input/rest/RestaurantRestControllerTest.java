package com.pragma.plazoleta.infrastructue.input.rest;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @Mock
    private IRestaurantHandler restaurantHandler;

    @InjectMocks
    private RestaurantRestController restaurantRestController;

    @Test
    void saveRestaurant() {
    }

    @Test
    void saveRestaurant_Success() {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
        doNothing().when(restaurantHandler).saveRestaurant(restaurantRequestDto);

        ResponseEntity<String> response = restaurantRestController.saveRestaurant(restaurantRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Restaurante creado exitosamente", response.getBody());
        verify(restaurantHandler, times(1)).saveRestaurant(restaurantRequestDto);
    }

    @Test
    void saveRestaurant_Failure() {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
        ApplicationException exception = new ApplicationException("Error al guardar el restaurante");
        doThrow(exception).when(restaurantHandler).saveRestaurant(restaurantRequestDto);

        ResponseEntity<String> response = restaurantRestController.saveRestaurant(restaurantRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error al guardar el restaurante", response.getBody());
        verify(restaurantHandler, times(1)).saveRestaurant(restaurantRequestDto);
    }

    @Test
    void findRestaurantById_Success() {
        Long restaurantId = 1L;
        RestaurantRequestDto expectedDto = new RestaurantRequestDto();
        when(restaurantHandler.findRestaurantById(restaurantId)).thenReturn(expectedDto);

        ResponseEntity<RestaurantRequestDto> response = restaurantRestController.findRestaurantById(restaurantId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
        verify(restaurantHandler, times(1)).findRestaurantById(restaurantId);
    }

    @Test
    void findRestaurantById_Failure() {
        Long restaurantId = 1L;
        ApplicationException exception = new ApplicationException("Restaurante no encontrado");
        when(restaurantHandler.findRestaurantById(restaurantId)).thenThrow(exception);

        ResponseEntity<RestaurantRequestDto> response = restaurantRestController.findRestaurantById(restaurantId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Restaurante no encontrado", response.getBody().getName());
        verify(restaurantHandler, times(1)).findRestaurantById(restaurantId);
    }
}