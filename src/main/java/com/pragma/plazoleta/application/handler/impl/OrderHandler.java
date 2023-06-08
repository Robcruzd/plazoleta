package com.pragma.plazoleta.application.handler.impl;

import com.pragma.plazoleta.application.dto.request.OrderRequestDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IOrderDishesHandler;
import com.pragma.plazoleta.application.handler.IOrderHandler;
import com.pragma.plazoleta.application.handler.IValidateCustomerOrderActive;
import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.exception.DomainException;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderDishesHandler orderDishesHandler;
    private final IValidateCustomerOrderActive validateCustomerOrderActive;
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
}
