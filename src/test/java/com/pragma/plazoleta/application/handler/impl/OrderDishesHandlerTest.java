package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderDishesRequestDto;
import com.pragma.plazoleta.application.mapper.IOrderRequestMapper;
import com.pragma.plazoleta.domain.api.IOrderDishesServicePort;
import com.pragma.plazoleta.domain.model.OrderDishesModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDishesHandlerTest {

    @Mock
    private IOrderRequestMapper orderRequestMapper;

    @Mock
    private IOrderDishesServicePort orderDishesServicePort;

    @InjectMocks
    private OrderDishesHandler orderDishesHandler;

    @Test
    void saveOrderDishes() {
        List<OrderDishesRequestDto> orderDishesRequestDtoList = new ArrayList<>();
        Long orderId = 1L;
        List<OrderDishesModel> orderDishesModelList = new ArrayList<>();

        when(orderRequestMapper.toModel(orderDishesRequestDtoList)).thenReturn(orderDishesModelList);

        orderDishesHandler.saveOrderDishes(orderDishesRequestDtoList, orderId);

        verify(orderRequestMapper, times(1)).toModel(orderDishesRequestDtoList);
        verify(orderDishesServicePort, times(1)).saveOrderDishes(orderDishesModelList, orderId);
    }
}