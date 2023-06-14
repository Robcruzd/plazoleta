package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;
import com.pragma.plazoleta.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.plazoleta.domain.spi.IUsersFeignPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantEmployeeUseCaseTest {

    @Mock
    private IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;

    @Mock
    private IUsersFeignPersistencePort usersFeignPersistencePort;

    @Test
    public void testSaveRestaurantEmployee_Success() {
        Long restaurantId = 1L;
        UserRequestDto userRequestDto = new UserRequestDto();
        String token = "example-token";
        Long userId = 1L;

        when(usersFeignPersistencePort.saveUserEmployee(token, userRequestDto)).thenReturn(userId);

        RestaurantEmployeeUseCase restaurantEmployeeUseCase = new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort, usersFeignPersistencePort);

        restaurantEmployeeUseCase.saveRestaurantEmployee(restaurantId, userRequestDto, token);

        verify(usersFeignPersistencePort, times(1)).saveUserEmployee(token, userRequestDto);
        verify(restaurantEmployeePersistencePort, times(1)).saveRestaurantEmployee(any(RestaurantEmployeeModel.class));
    }

    @Test
    public void testSaveRestaurantEmployee_RequestException() {
        Long restaurantId = 1L;
        UserRequestDto userRequestDto = new UserRequestDto();
        String token = "example-token";

        when(usersFeignPersistencePort.saveUserEmployee(token, userRequestDto)).thenThrow(new RequestException("Request exception message", HttpStatus.BAD_REQUEST));

        RestaurantEmployeeUseCase restaurantEmployeeUseCase = new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort, usersFeignPersistencePort);

        assertThrows(DomainException.class, () -> restaurantEmployeeUseCase.saveRestaurantEmployee(restaurantId, userRequestDto, token));
    }
}