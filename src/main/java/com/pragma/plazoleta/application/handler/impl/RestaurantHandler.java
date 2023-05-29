package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import com.pragma.plazoleta.application.mapper.IRestaurantRequesMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequesMapper restaurantRequesMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        try {
            RestaurantModel restaurantModel = this.restaurantRequesMapper.toRestaurantModel(restaurantRequestDto);
            this.restaurantServicePort.saveRestaurant(restaurantModel);
        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public RestaurantRequestDto findRestaurantById(Long restaurantId) {
        try {
            RestaurantModel restaurantModel = this.restaurantServicePort.findRestaurantById(restaurantId);
            return restaurantRequesMapper.toRestaurantDto(restaurantModel);
        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
