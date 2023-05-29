package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.DishModel;

public interface IDishPersistencePort {

    DishModel saveDish(DishModel dishModel);

    void updateDish(DishModel dishModel);
}
