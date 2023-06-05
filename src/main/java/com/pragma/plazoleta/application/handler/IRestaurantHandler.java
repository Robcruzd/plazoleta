package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import com.pragma.plazoleta.application.dto.response.RestaurantListResponseDto;

import java.util.List;


public interface IRestaurantHandler {

    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    RestaurantRequestDto findRestaurantById(Long restaurantId);

    List<RestaurantListResponseDto> listRestaurants(int page, int size);
}
