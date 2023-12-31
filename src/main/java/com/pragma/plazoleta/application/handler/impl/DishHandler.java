package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.DishEnableDisableRequestDto;
import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.DishListResponseDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IDishHandler;
import com.pragma.plazoleta.application.handler.IValidateOwnerRestaurant;
import com.pragma.plazoleta.application.mapper.IDishRequestMapper;
import com.pragma.plazoleta.application.mapper.IDishResponseMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantRequesMapper;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
    public void saveDish(DishRequestDto dishRequestDto, String token) {
        try {
            DishResponseDto dishResponseDto = this.dishResponseMapper.toDishResDto(dishRequestDto);
            RestaurantModel restaurantModel = this.restaurantServicePort.findRestaurantById(dishRequestDto.getRestaurantRequestDto());
            RestaurantRequestDto restaurantRequestDto = restaurantRequesMapper.toRestaurantDto(restaurantModel);
            validateOwnerRestaurant.validate(restaurantRequestDto, token);
            dishResponseDto.setRestaurantRequestDto(restaurantRequestDto);
            DishModel dishModel = this.dishResponseMapper.toDishModel(dishResponseDto);
            dishModel.setActive(true);
            dishModel.validate();
            this.dishServicePort.saveDish(dishModel);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void updateDish(DishUpdateRequestDto dishUpdateRequestDto, String token) {
        try {
            DishModel dishModel = dishServicePort.findDishById(dishUpdateRequestDto.getId());
            RestaurantModel restaurantModel = restaurantServicePort.findRestaurantById(dishModel.getRestaurantModel().getId());
            RestaurantRequestDto restaurantRequestDto = restaurantRequesMapper.toRestaurantDto(restaurantModel);
            validateOwnerRestaurant.validate(restaurantRequestDto, token);
            dishModel.setDescription(dishUpdateRequestDto.getDescription());
            dishModel.setPrice(dishUpdateRequestDto.getPrecio());
            dishModel.validate();
            this.dishServicePort.saveDish(dishModel);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public DishResponseDto findDishById(Long dishId) {
        DishModel dishModel = this.dishServicePort.findDishById(dishId);
        return this.dishResponseMapper.toDishResDtoFromModel(dishModel);
    }

    @Override
    public void enableDisableDish(DishEnableDisableRequestDto dishEnableDisableRequestDto, String token) {
        try {
            DishModel dishModel = dishServicePort.findDishById(dishEnableDisableRequestDto.getDishId());
            RestaurantModel restaurantModel = restaurantServicePort.findRestaurantById(dishModel.getRestaurantModel().getId());
            RestaurantRequestDto restaurantRequestDto = restaurantRequesMapper.toRestaurantDto(restaurantModel);
            validateOwnerRestaurant.validate(restaurantRequestDto, token);
            dishModel.setActive(!dishModel.isActive());
            dishModel.validate();
            this.dishServicePort.saveDish(dishModel);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<DishListResponseDto> listDishes(Long restaurantId, Long categoryId, int page, int size) {
        try {
            List<DishModel> dishesModel = dishServicePort.findDishesByRestaurantIdAndCategoryId(restaurantId, categoryId, page, size);
            return dishResponseMapper.toDishListDto(dishesModel);
        } catch (DomainException e) {
            throw new ApplicationException(e.getMessage());
        }
    }


}
