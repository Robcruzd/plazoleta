package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IValidateOwnerRestaurant;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateOwnerRestaurant implements IValidateOwnerRestaurant {

    private final JwtService jwtService;

    @Override
    public void validate(RestaurantRequestDto restaurant, String token) {
        String jwt = jwtService.parseJwt(token);

        if (!jwtService.isValidToken(jwt))
            throw new RequestException("Token invalido",
                    HttpStatus.BAD_REQUEST);

        Long ownerId = jwtService.extractUserId(jwt);

        if (restaurant.getOwnerId() != ownerId)
            throw new RequestException("El restaurante no pertenece a este propietario",
                    HttpStatus.BAD_REQUEST);
    }
}
