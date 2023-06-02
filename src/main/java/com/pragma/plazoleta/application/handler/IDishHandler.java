package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto, String token);

    void updateDish(DishUpdateRequestDto dishUpdateRequestDto, String token);

    DishResponseDto findDishById(Long dishId);
}
