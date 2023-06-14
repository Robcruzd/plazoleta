package com.pragma.plazoleta.infrastructue.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantEmployeeEntityId implements Serializable {

    private Long employeeId;
    private Long restaurantId;
}
