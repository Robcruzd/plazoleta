package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.RestaurantListResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IRestaurantHandler;
import com.pragma.plazoleta.application.mapper.IRestaurantRequesMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantResponseMapper;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantRequesMapper restaurantRequesMapper;
    private final IRestaurantResponseMapper restaurantResponseMapper;

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

    @Override
    public List<RestaurantListResponseDto> listRestaurants(int page, int size) {
        try {
            List<RestaurantModel> restaurantModel = this.restaurantServicePort.findAllRestaurants(page, size);
            return restaurantResponseMapper.toRestaurantListDto(restaurantModel);
        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
