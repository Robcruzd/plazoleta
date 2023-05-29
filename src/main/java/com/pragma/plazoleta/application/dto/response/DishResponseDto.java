package com.pragma.plazoleta.application.dto.response;

import com.pragma.plazoleta.application.dto.request.CategoryRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishResponseDto {

    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private CategoryRequestDto categoryRequestDto;
    private RestaurantRequestDto restaurantRequestDto;
    private boolean active;
}
