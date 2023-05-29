package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.DishModel;

public interface IDishServicePort {

    void saveDish(DishModel dishModel);

    DishModel findDishById(Long dishId);

}
