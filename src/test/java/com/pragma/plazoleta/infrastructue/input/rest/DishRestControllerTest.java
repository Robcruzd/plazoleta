package com.pragma.plazoleta.infrastructue.input.rest;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IDishHandler;
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
class DishRestControllerTest {

    @Mock
    private IDishHandler dishHandler;

    @InjectMocks
    private DishRestController dishRestController;

    @Test
    void saveDish_Success() {
        DishRequestDto dishRequestDto = new DishRequestDto();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Plato creado exitosamente");
        doNothing().when(dishHandler).saveDish(dishRequestDto);

        ResponseEntity<String> response = dishRestController.saveDish(dishRequestDto);

        assertEquals(expectedResponse, response);
        verify(dishHandler, times(1)).saveDish(dishRequestDto);
    }

    @Test
    void saveDish_ApplicationException() {
        DishRequestDto dishRequestDto = new DishRequestDto();
        String errorMessage = "Información invalida del plato";
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        doThrow(new ApplicationException(errorMessage)).when(dishHandler).saveDish(dishRequestDto);

        ResponseEntity<String> response = dishRestController.saveDish(dishRequestDto);

        assertEquals(expectedResponse, response);
        verify(dishHandler, times(1)).saveDish(dishRequestDto);
    }

    @Test
    void updateDish_Success() {
        DishUpdateRequestDto dishUpdateRequestDto = new DishUpdateRequestDto();
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Plato actualizado exitosamente");
        doNothing().when(dishHandler).updateDish(dishUpdateRequestDto);

        ResponseEntity<String> response = dishRestController.updateDish(dishUpdateRequestDto);

        assertEquals(expectedResponse, response);
        verify(dishHandler, times(1)).updateDish(dishUpdateRequestDto);
    }

    @Test
    void updateDish_ApplicationException() {
        DishUpdateRequestDto dishUpdateRequestDto = new DishUpdateRequestDto();
        String errorMessage = "Información invalida del plato";
        ResponseEntity<String> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        doThrow(new ApplicationException(errorMessage)).when(dishHandler).updateDish(dishUpdateRequestDto);

        ResponseEntity<String> response = dishRestController.updateDish(dishUpdateRequestDto);

        assertEquals(expectedResponse, response);
        verify(dishHandler, times(1)).updateDish(dishUpdateRequestDto);
    }

    @Test
    void findDishById_Success() {
        Long dishId = 1L;
        DishResponseDto expectedResponseDto = new DishResponseDto();
        ResponseEntity<DishResponseDto> expectedResponse = ResponseEntity.ok(expectedResponseDto);
        when(dishHandler.findDishById(dishId)).thenReturn(expectedResponseDto);

        ResponseEntity<DishResponseDto> response = dishRestController.findDishById(dishId);

        assertEquals(expectedResponse, response);
        verify(dishHandler, times(1)).findDishById(dishId);
    }

    @Test
    void findDishById_ApplicationException() {
        Long dishId = 1L;
        String errorMessage = "Plato no encontrado";
        DishResponseDto expectedResponseDto = new DishResponseDto();
        expectedResponseDto.setName(errorMessage);
        ResponseEntity<DishResponseDto> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(expectedResponseDto);
        when(dishHandler.findDishById(dishId)).thenThrow(new ApplicationException(errorMessage));

        ResponseEntity<DishResponseDto> response = dishRestController.findDishById(dishId);

        assertEquals(expectedResponse.getBody().getName(), response.getBody().getName());
        verify(dishHandler, times(1)).findDishById(dishId);
    }
}