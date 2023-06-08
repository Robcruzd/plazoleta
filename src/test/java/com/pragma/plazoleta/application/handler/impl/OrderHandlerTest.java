package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.handler.IOrderDishesHandler;
import com.pragma.plazoleta.application.handler.IValidateCustomerOrderActive;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderHandlerTest {

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderDishesHandler orderDishesHandler;

    @Mock
    private IValidateCustomerOrderActive validateCustomerOrderActive;

    @InjectMocks
    private OrderHandler orderHandler;

    @Test
    void SaveOrder() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        String token = "token";
        Long customerId = 1L;
        Long orderId = 2L;

        when(validateCustomerOrderActive.validate(token)).thenReturn(customerId);
        when(orderServicePort.saveOrder(customerId, orderRequestDto.getRestaurantId())).thenReturn(orderId);

        orderHandler.saveOrder(orderRequestDto, token);

        verify(validateCustomerOrderActive, times(1)).validate(token);
        verify(orderServicePort, times(1)).saveOrder(customerId, orderRequestDto.getRestaurantId());
        verify(orderDishesHandler, times(1)).saveOrderDishes(orderRequestDto.getOrderDishesList(), orderId);
    }
}