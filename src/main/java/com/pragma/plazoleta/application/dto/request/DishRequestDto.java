package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishRequestDto {

    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private Long categoryRequestDto;
    private Long restaurantRequestDto;
}
