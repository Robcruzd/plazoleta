package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IDishServicePort;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.domain.exception.DomainException;

import java.util.List;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void saveDish(DishModel dishModel) {
        try {
            this.dishPersistencePort.saveDish(dishModel);
        } catch (DomainException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @Override
    public DishModel findDishById(Long dishId) {
        try {
            return this.dishPersistencePort.findDishById(dishId);
        } catch (DomainException e) {
            throw new DomainException(e.getMessage());
        }
    }

    @Override
    public List<DishModel> findDishesByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId, int page, int size) {
        return dishPersistencePort.findDishesByRestaurantIdAndCategoryId(restaurantId, categoryId, page, size);
    }
}
