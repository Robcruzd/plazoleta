package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.OrderDishesRequestDto;

import java.util.List;
import java.util.Set;

public interface IOrderDishesHandler {

    void saveOrderDishes(List<OrderDishesRequestDto> orderDishesRequestDtoList, Long orderId);
}
