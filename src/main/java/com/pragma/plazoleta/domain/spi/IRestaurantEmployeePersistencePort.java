package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;

public interface IRestaurantEmployeePersistencePort {

    void saveRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);
}
