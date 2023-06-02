package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateOwnerRestaurantTest {

    @InjectMocks
    private ValidateOwnerRestaurant validator;
    private RestaurantRequestDto validRequestDto;
    private RestaurantRequestDto invalidRequestDto;
    @Mock
    private JwtService jwtService;
    private Long ownerId;
    private String token;
    private String jwt;

    @BeforeEach
    void setUp() {
        validator = new ValidateOwnerRestaurant(jwtService);
        ownerId = 1L;
        token = "token";
        jwt = "token_jwt";


        validRequestDto = new RestaurantRequestDto();
        validRequestDto.setOwnerId(ownerId);

        invalidRequestDto = new RestaurantRequestDto();
        invalidRequestDto.setOwnerId(2L);
    }

    @Test
    void validateTest_ValidOwner() {

        when(jwtService.parseJwt(token)).thenReturn(jwt);
        when(jwtService.isValidToken(jwt)).thenReturn(true);
        when(jwtService.extractUserId(jwt)).thenReturn(ownerId = 1l);

        assertDoesNotThrow(() -> validator.validate(validRequestDto, token));
    }

    @Test
    void validateTest_TokenInvalid() {
        when(jwtService.parseJwt(token)).thenReturn(jwt);
        when(jwtService.isValidToken(jwt)).thenReturn(false);

        assertThrows(RequestException.class,
                () -> validator.validate(validRequestDto, token));
    }

    @Test
    void validateTest_Exception() {
        when(jwtService.parseJwt(token)).thenReturn(jwt);
        when(jwtService.isValidToken(jwt)).thenReturn(true);
        when(jwtService.extractUserId(jwt)).thenReturn(1l);

        RequestException exception = assertThrows(RequestException.class,
                ()->validator.validate(invalidRequestDto, token));
        assertEquals("El restaurante no pertenece a este propietario", exception.getMessage());
    }
}