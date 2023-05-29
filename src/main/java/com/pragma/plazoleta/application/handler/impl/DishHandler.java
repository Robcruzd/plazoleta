package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IDishHandler;
import com.pragma.plazoleta.application.handler.IValidateOwnerRestaurant;
import com.pragma.plazoleta.application.mapper.IDishRequestMapper;
import com.pragma.plazoleta.application.mapper.IDishResponseMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantRequesMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequesMapper restaurantRequesMapper;
    private final IValidateOwnerRestaurant validateOwnerRestaurant;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        try {
            Long ownerId = 1L;
            DishResponseDto dishResponseDto = this.dishResponseMapper.toDishResDto(dishRequestDto);
            RestaurantModel restaurantModel = this.restaurantServicePort.findRestaurantById(dishRequestDto.getRestaurantRequestDto());
            RestaurantRequestDto restaurantRequestDto = restaurantRequesMapper.toRestaurantDto(restaurantModel);
            if (validateOwnerRestaurant.validate(restaurantRequestDto, ownerId))
                dishResponseDto.setRestaurantRequestDto(restaurantRequestDto);
            DishModel dishModel = this.dishResponseMapper.toDishModel(dishResponseDto);
            dishModel.validate();
            this.dishServicePort.saveDish(dishModel);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
