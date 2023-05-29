package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IValidateOwnerRestaurant;
import org.springframework.stereotype.Service;

@Service
public class ValidateOwnerRestaurant implements IValidateOwnerRestaurant {
    @Override
    public boolean validate(RestaurantRequestDto restaurantRequestDto, Long ownerId) {
        if(restaurantRequestDto.getOwnerId() == ownerId){
            return true;
        } else {
            throw new ApplicationException("El restaurante no pertenece a este propietario");
        }
    }
}
