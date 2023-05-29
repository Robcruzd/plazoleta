package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.request.DishUpdateRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;

public interface IDishHandler {

    void saveDish(DishRequestDto dishRequestDto);

    void updateDish(DishUpdateRequestDto dishUpdateRequestDto);

    DishResponseDto findDishById(Long dishId);
}
