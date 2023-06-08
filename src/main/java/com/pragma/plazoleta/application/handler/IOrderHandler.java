package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;

public interface IOrderHandler {

    void saveOrder(OrderRequestDto orderRequestDto, String token);
}
