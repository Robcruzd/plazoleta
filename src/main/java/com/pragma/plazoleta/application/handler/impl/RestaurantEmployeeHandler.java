package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeFullRequestDto;
import com.pragma.plazoleta.application.dto.request.UserRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IRestaurantEmployeeHandler;
import com.pragma.plazoleta.application.mapper.IRestaurantEmployeeRequestMapper;
import com.pragma.plazoleta.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantEmployeeHandler implements IRestaurantEmployeeHandler {

    private final IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;
    private final IRestaurantEmployeeServicePort restaurantEmployeeServicePort;
    @Override
    public void saveRestaurantEmployee(RestaurantEmployeeFullRequestDto restaurantEmployeeFullRequestDto, String token) {
        try {
            UserRequestDto userRequestDto = restaurantEmployeeRequestMapper.toUserRequestDto(restaurantEmployeeFullRequestDto);
            restaurantEmployeeServicePort.saveRestaurantEmployee(restaurantEmployeeFullRequestDto.getRestaurantId(),
                    userRequestDto, token);
        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
