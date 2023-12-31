package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderDishesRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderDishesResponseDto;
import com.pragma.plazoleta.application.handler.IOrderDishesHandler;
import com.pragma.plazoleta.application.mapper.IOrderDishesResponseMapper;
import com.pragma.plazoleta.application.mapper.IOrderRequestMapper;
import com.pragma.plazoleta.domain.api.IOrderDishesServicePort;
import com.pragma.plazoleta.domain.model.OrderDishesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDishesHandler implements IOrderDishesHandler {

    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderDishesResponseMapper orderDishesResponseMapper;
    private final IOrderDishesServicePort orderDishesServicePort;
    @Override
    public void saveOrderDishes(List<OrderDishesRequestDto> orderDishesRequestDtoList, Long orderId) {
        List<OrderDishesModel> orderDishesModel = orderRequestMapper.toModel(orderDishesRequestDtoList);
        orderDishesServicePort.saveOrderDishes(orderDishesModel, orderId);
    }

    @Override
    public List<OrderDishesResponseDto> findOrderDishesByOrderId(Long orderId) {
        List<OrderDishesModel> orderDishesModelList = orderDishesServicePort.findOrderDishesByOrderId(orderId);
        return orderDishesResponseMapper.toResponseDto(orderDishesModelList);
    }
}
