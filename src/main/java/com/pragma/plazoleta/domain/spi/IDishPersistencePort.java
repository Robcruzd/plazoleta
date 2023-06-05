package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.DishModel;

import java.util.List;

public interface IDishPersistencePort {

    DishModel saveDish(DishModel dishModel);

    DishModel findDishById(Long dishId);

    List<DishModel> findDishesByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId, int page, int size);
}
