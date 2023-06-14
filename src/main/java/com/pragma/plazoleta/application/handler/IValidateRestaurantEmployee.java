package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;

public interface IValidateRestaurantEmployee {

    RestaurantEmployeeRequestDto validate(String token);
}
