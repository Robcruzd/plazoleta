package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.RestaurantModel;

public interface IRestaurantPersistencePort {

    void saveRestaurant(RestaurantModel restaurantModel);

    RestaurantModel findRestaurantById(Long restaurantId);
}
