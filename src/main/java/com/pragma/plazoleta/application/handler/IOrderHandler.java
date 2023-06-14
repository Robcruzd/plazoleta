package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;

import java.util.List;

public interface IOrderHandler {

    void saveOrder(OrderRequestDto orderRequestDto, String token);

    List<OrderResponseDto> listOrders(String token, int status, int page, int size);

    void updateOrders(List<UpdateOrderRequestDto> updateOrderRequestDtoList, String token);
}
