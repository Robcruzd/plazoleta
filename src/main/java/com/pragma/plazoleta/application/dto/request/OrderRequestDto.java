package com.pragma.plazoleta.application.dto.request;

import com.pragma.plazoleta.domain.model.StatusOrderModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {

    private Long restaurantId;
    private List<OrderDishesRequestDto> orderDishesList;
}
