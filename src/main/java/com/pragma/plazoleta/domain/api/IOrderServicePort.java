package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;

import java.util.List;

public interface IOrderServicePort {

    Long saveOrder(Long customerId, Long restaurantId);

    List<OrderModel> listOrders(Long restaurantId, int status, int page, int size);

    void updateOrders(Long userId, List<OrderModel> orderModelList);

    void updateOrderReady(OrderModel orderModel, String token);

    void updateOrderDeliver(OrderModel orderModel, String token);
}
