package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeFullRequestDto;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.mapper.IRestaurantEmployeeRequestMapper;
import com.pragma.plazoleta.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
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
class RestaurantEmployeeHandlerTest {

    @InjectMocks
    private RestaurantEmployeeHandler restaurantEmployeeHandler;

    @Mock
    private IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;

    @Mock
    private IRestaurantEmployeeServicePort restaurantEmployeeServicePort;

    @Test
    public void testSaveRestaurantEmployee_Success() {
        RestaurantEmployeeFullRequestDto restaurantEmployeeFullRequestDto = new RestaurantEmployeeFullRequestDto();
        String token = "token";
        UserRequestDto userRequestDto = new UserRequestDto();

        when(restaurantEmployeeRequestMapper.toUserRequestDto(restaurantEmployeeFullRequestDto)).thenReturn(userRequestDto);

        restaurantEmployeeHandler.saveRestaurantEmployee(restaurantEmployeeFullRequestDto, token);

        verify(restaurantEmployeeRequestMapper, times(1)).toUserRequestDto(restaurantEmployeeFullRequestDto);
        verify(restaurantEmployeeServicePort, times(1)).saveRestaurantEmployee(restaurantEmployeeFullRequestDto.getRestaurantId(), userRequestDto, token);
    }

    @Test
    public void testSaveRestaurantEmployee_DomainException() {
        RestaurantEmployeeFullRequestDto restaurantEmployeeFullRequestDto = new RestaurantEmployeeFullRequestDto();
        String token = "token";

        when(restaurantEmployeeRequestMapper.toUserRequestDto(restaurantEmployeeFullRequestDto)).thenThrow(new DomainException("Domain exception message"));

        assertThrows(ApplicationException.class, () -> restaurantEmployeeHandler.saveRestaurantEmployee(restaurantEmployeeFullRequestDto, token));
    }
}