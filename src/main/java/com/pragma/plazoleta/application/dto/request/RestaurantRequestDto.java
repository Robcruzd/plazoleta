package com.pragma.plazoleta.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDto {

    private Long id;
    private String name;
    private Long nit;
    private String address;
    private String phone;
    private String urlLogo;
    private Long ownerId;
}
