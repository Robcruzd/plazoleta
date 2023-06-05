package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.RestaurantModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestaurantServicePort {

    void saveRestaurant(RestaurantModel restaurantModel);

    RestaurantModel findRestaurantById(Long restaurantId);

    List<RestaurantModel> findAllRestaurants(int page, int size);
}
