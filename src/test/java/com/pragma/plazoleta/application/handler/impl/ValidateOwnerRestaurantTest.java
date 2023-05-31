package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateOwnerRestaurantTest {

    private ValidateOwnerRestaurant validator;
    private RestaurantRequestDto validRequestDto;
    private RestaurantRequestDto invalidRequestDto;
    private Long ownerId;

    @BeforeEach
    void setUp() {
        validator = new ValidateOwnerRestaurant();
        ownerId = 1L;

        validRequestDto = new RestaurantRequestDto();
        validRequestDto.setOwnerId(ownerId);

        invalidRequestDto = new RestaurantRequestDto();
        invalidRequestDto.setOwnerId(2L);
    }

    @Test
    void testValidate_ValidOwner() {
        boolean result = validator.validate(validRequestDto, ownerId);
        assertTrue(result, "El validador debería retornar true para un propietario válido");
    }

    @Test
    void testValidate_InvalidOwner() {
        assertThrows(ApplicationException.class, () -> validator.validate(invalidRequestDto, ownerId),
                "El validador debería lanzar una ApplicationException para un propietario inválido");
    }
}