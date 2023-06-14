package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderUpdateResponseDto;

import java.util.List;

public interface IValidateOrderWithRestaurant {

    List<OrderUpdateResponseDto> validate(List<UpdateOrderRequestDto> updateOrderRequestDtoList, Long restaurantId);
}
