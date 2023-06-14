package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeFullRequestDto;

public interface IRestaurantEmployeeHandler {

    void saveRestaurantEmployee(RestaurantEmployeeFullRequestDto restaurantEmployeeFullRequestDto, String token);
}
