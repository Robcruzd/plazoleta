package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.DishEnableDisableRequestDto;
import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.response.DishListResponseDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;

import java.util.List;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto, String token);

    void updateDish(DishUpdateRequestDto dishUpdateRequestDto, String token);

    DishResponseDto findDishById(Long dishId);

    void enableDisableDish(DishEnableDisableRequestDto dishEnableDisableRequestDto, String token);

    List<DishListResponseDto> listDishes(Long restaurantId, Long categoryId, int page, int size);
}
