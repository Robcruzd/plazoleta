package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishesResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderUpdateResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IOrderDishesHandler;
import com.pragma.plazoleta.application.handler.IValidateCustomerOrderActive;
import com.pragma.plazoleta.application.handler.IValidateOrderWithRestaurant;
import com.pragma.plazoleta.application.handler.IValidateRestaurantEmployee;
import com.pragma.plazoleta.application.mapper.IOrderRequestMapper;
import com.pragma.plazoleta.application.mapper.IOrderResponseMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantEmployeeRequestMapper;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
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

    @Mock
    private IOrderRequestMapper orderRequestMapper;

    @Mock
    private IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;

    @Mock
    private IValidateOrderWithRestaurant validateOrderWithRestaurant;


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

        RestaurantEmployeeRequestDto restaurantEmployeeRequestDto = new RestaurantEmployeeRequestDto(1l, 1l);

        when(validateRestaurantEmployee.validate(token)).thenReturn(restaurantEmployeeRequestDto);
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

    @Test
    public void testUpdateOrders_WithValidData_CallsOrderServicePort() {
        String token = "token";

        RestaurantEmployeeRequestDto restaurantEmployeeRequestDto = new RestaurantEmployeeRequestDto();

        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();

        List<UpdateOrderRequestDto> updateOrderRequestDtoList = new ArrayList<>();

        List<OrderUpdateResponseDto> orderUpdateResponseDtoList = new ArrayList<>();

        List<OrderModel> orderModelList = new ArrayList<>();

        when(validateRestaurantEmployee.validate(token)).thenReturn(restaurantEmployeeRequestDto);
        when(restaurantEmployeeRequestMapper.toModel(restaurantEmployeeRequestDto)).thenReturn(restaurantEmployeeModel);
        when(validateOrderWithRestaurant.validate(updateOrderRequestDtoList, restaurantEmployeeModel.getRestaurantId())).thenReturn(orderUpdateResponseDtoList);
        when(orderRequestMapper.toOrderModelList(orderUpdateResponseDtoList)).thenReturn(orderModelList);

        assertDoesNotThrow(() -> orderHandler.updateOrders(updateOrderRequestDtoList, token));

        verify(validateRestaurantEmployee, times(1)).validate(token);
        verify(restaurantEmployeeRequestMapper, times(1)).toModel(restaurantEmployeeRequestDto);
        verify(validateOrderWithRestaurant, times(1)).validate(updateOrderRequestDtoList, restaurantEmployeeModel.getRestaurantId());
        verify(orderRequestMapper, times(1)).toOrderModelList(orderUpdateResponseDtoList);
        verify(orderServicePort, times(1)).updateOrders(restaurantEmployeeModel.getUserId(), orderModelList);
    }

    @Test
    public void testUpdateOrders_WithException_ThrowsApplicationException() {
        String token = "token";

        when(validateRestaurantEmployee.validate(token)).thenThrow(new DomainException("Validation failed"));

        assertThrows(ApplicationException.class, () -> orderHandler.updateOrders(new ArrayList<>(), token));

        verify(validateRestaurantEmployee, times(1)).validate(token);
        verifyNoMoreInteractions(restaurantEmployeeRequestMapper, validateOrderWithRestaurant, orderRequestMapper, orderServicePort);
    }
}