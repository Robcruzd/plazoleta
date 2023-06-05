package com.pragma.plazoleta.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishListResponseDto {

    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private boolean active;
}
