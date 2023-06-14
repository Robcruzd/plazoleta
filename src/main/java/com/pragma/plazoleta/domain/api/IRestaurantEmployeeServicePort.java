package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeFullRequestDto;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;

public interface IRestaurantEmployeeServicePort {

    void saveRestaurantEmployee(Long restaurantId, UserRequestDto userRequestDto, String token);
}
