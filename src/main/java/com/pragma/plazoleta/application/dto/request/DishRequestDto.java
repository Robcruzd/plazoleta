package com.pragma.plazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishRequestDto {

    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private Long categoryRequestDto;
    private Long restaurantRequestDto;
}
