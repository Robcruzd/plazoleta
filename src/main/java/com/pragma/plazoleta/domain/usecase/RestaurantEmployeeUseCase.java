package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;
import com.pragma.plazoleta.domain.spi.IRestaurantEmployeePersistencePort;
import com.pragma.plazoleta.domain.spi.IUsersFeignPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;

public class RestaurantEmployeeUseCase implements IRestaurantEmployeeServicePort {

    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IUsersFeignPersistencePort usersFeignPersistencePort;

    public RestaurantEmployeeUseCase(IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IUsersFeignPersistencePort usersFeignPersistencePort) {
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.usersFeignPersistencePort = usersFeignPersistencePort;
    }

    @Override
    public void saveRestaurantEmployee(Long restaurantId, UserRequestDto userRequestDto, String token) {
        try {
            Long userId = usersFeignPersistencePort.saveUserEmployee(token, userRequestDto);
            RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel(restaurantId, userId);
            restaurantEmployeePersistencePort.saveRestaurantEmployee(restaurantEmployeeModel);
        } catch (RequestException e) {
            throw new DomainException(e.getMessage());
        }
    }
}
