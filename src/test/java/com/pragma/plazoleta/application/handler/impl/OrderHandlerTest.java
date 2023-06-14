package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishesResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IOrderDishesHandler;
import com.pragma.plazoleta.application.handler.IValidateCustomerOrderActive;
import com.pragma.plazoleta.application.handler.IValidateRestaurantEmployee;
import com.pragma.plazoleta.application.mapper.IOrderResponseMapper;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.OrderModel;
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
class OrderHandlerTest {

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderDishesHandler orderDishesHandler;

    @Mock
    private IValidateCustomerOrderActive validateCustomerOrderActive;
    @Mock
    private IValidateRestaurantEmployee validateRestaurantEmployee;
    @Mock
    private IOrderResponseMapper orderResponseMapper;


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

    @Test
    public void testListOrders_Success() {
        String token = "token";
        int status = 1;
        int page = 1;
        int size = 10;
        Long restaurantId = 1L;

        List<OrderModel> orderModelList = new ArrayList<>();
        OrderModel orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModelList.add(orderModel);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(1L);
        orderResponseDtoList.add(orderResponseDto);

        List<OrderDishesResponseDto> orderDishesResponseDtoList = new ArrayList<>();
        OrderDishesResponseDto orderDishesResponseDto = new OrderDishesResponseDto();
        orderDishesResponseDto.setDishId(1L);
        orderDishesResponseDtoList.add(orderDishesResponseDto);

        when(validateRestaurantEmployee.validate(token)).thenReturn(restaurantId);
        when(orderServicePort.listOrders(restaurantId, status, page, size)).thenReturn(orderModelList);
        when(orderResponseMapper.toOrderDto(orderModelList)).thenReturn(orderResponseDtoList);
        when(orderDishesHandler.findOrderDishesByOrderId(orderResponseDto.getId())).thenReturn(orderDishesResponseDtoList);

        List<OrderResponseDto> result = orderHandler.listOrders(token, status, page, size);

        verify(validateRestaurantEmployee, times(1)).validate(token);
        verify(orderServicePort, times(1)).listOrders(restaurantId, status, page, size);
        verify(orderResponseMapper, times(1)).toOrderDto(orderModelList);
        verify(orderDishesHandler, times(1)).findOrderDishesByOrderId(orderResponseDto.getId());

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(orderDishesResponseDtoList, result.get(0).getOrderDishesResponseDtoList());
    }

    @Test
    public void testListOrders_DomainException() {
        String token = "token";
        int status = 1;
        int page = 1;
        int size = 10;

        when(validateRestaurantEmployee.validate(token)).thenThrow(new DomainException("Domain exception message"));

        assertThrows(ApplicationException.class, () -> orderHandler.listOrders(token, status, page, size));
    }
}