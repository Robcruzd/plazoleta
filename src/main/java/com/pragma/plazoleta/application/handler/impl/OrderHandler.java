package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateOrderDeliverRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishesResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.application.dto.response.OrderUpdateResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IOrderDishesHandler;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import com.pragma.plazoleta.application.handler.IValidateCustomerOrderActive;
import com.pragma.plazoleta.application.handler.IValidateOrderWithEmployee;
import com.pragma.plazoleta.application.handler.IValidateOrderWithRestaurant;
import com.pragma.plazoleta.application.handler.IValidateRestaurantEmployee;
import com.pragma.plazoleta.application.mapper.IOrderRequestMapper;
import com.pragma.plazoleta.application.mapper.IOrderResponseMapper;
import com.pragma.plazoleta.application.mapper.IRestaurantEmployeeRequestMapper;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.security.jwt.IExtractAndValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderDishesHandler orderDishesHandler;
    private final IValidateCustomerOrderActive validateCustomerOrderActive;
    private final IValidateRestaurantEmployee validateRestaurantEmployee;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderRequestMapper orderRequestMapper;
    private final IRestaurantEmployeeRequestMapper restaurantEmployeeRequestMapper;
    private final IValidateOrderWithRestaurant validateOrderWithRestaurant;
    private final IValidateOrderWithEmployee validateOrderWithEmployee;
    @Override
    public void saveOrder(OrderRequestDto orderRequestDto, String token) {
        try {
            Long customerId = validateCustomerOrderActive.validate(token);
            Long orderId = orderServicePort.saveOrder(customerId, orderRequestDto.getRestaurantId());
            if(orderId != null)
                orderDishesHandler.saveOrderDishes(orderRequestDto.getOrderDishesList(), orderId);
        } catch (DomainException | RequestException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<OrderResponseDto> listOrders(String token, int status, int page, int size) {
        try {
            RestaurantEmployeeRequestDto restaurantEmployeeRequestDto = validateRestaurantEmployee.validate(token);
            List<OrderModel> orderModelList = orderServicePort.listOrders(restaurantEmployeeRequestDto.getRestaurantId(), status, page, size);
            List<OrderResponseDto> orderResponseDtoList = orderResponseMapper.toOrderDto(orderModelList);
            for(OrderResponseDto orderResponseDto: orderResponseDtoList) {
                List<OrderDishesResponseDto> orderDishesResponseDtoList = orderDishesHandler.findOrderDishesByOrderId(orderResponseDto.getId());
                orderResponseDto.setOrderDishesResponseDtoList(orderDishesResponseDtoList);
            }
            return orderResponseDtoList;
        } catch (DomainException | RequestException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void updateOrders(List<UpdateOrderRequestDto> updateOrderRequestDtoList, String token) {
        try {
            RestaurantEmployeeRequestDto restaurantEmployeeRequestDto = validateRestaurantEmployee.validate(token);
            RestaurantEmployeeModel restaurantEmployeeModel = restaurantEmployeeRequestMapper.toModel(restaurantEmployeeRequestDto);
            List<OrderUpdateResponseDto> orderUpdateResponseDtoList = validateOrderWithRestaurant.validate(updateOrderRequestDtoList, restaurantEmployeeModel.getRestaurantId());
            List<OrderModel> orderModelList = orderRequestMapper.toOrderModelList(orderUpdateResponseDtoList);
            orderServicePort.updateOrders(restaurantEmployeeModel.getUserId(), orderModelList);
        } catch (DomainException | RequestException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void updateOrderReady(UpdateOrderRequestDto updateOrderRequestDto, String token) {
        try {
            OrderModel orderModel = validateOrderWithEmployee.validate(token, updateOrderRequestDto.getId(), null);
            orderServicePort.updateOrderReady(orderModel, token);
        } catch (DomainException | RequestException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public void updateOrderDeliver(UpdateOrderDeliverRequestDto updateOrderDeliverRequestDto, String token) {
        try {
            OrderModel orderModel = validateOrderWithEmployee.validate(token, updateOrderDeliverRequestDto.getId(), updateOrderDeliverRequestDto.getSecurityPin());
            orderServicePort.updateOrderDeliver(orderModel, token);
        } catch (DomainException | RequestException e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
