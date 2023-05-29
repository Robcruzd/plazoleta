package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;

public interface IValidateOwnerRestaurant {

    boolean validate(RestaurantRequestDto restaurantRequestDto, Long ownerId);
}
