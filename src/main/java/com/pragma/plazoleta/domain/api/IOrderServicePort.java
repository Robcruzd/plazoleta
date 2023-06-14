package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.OrderModel;

import java.util.List;

public interface IOrderServicePort {

    Long saveOrder(Long customerId, Long restaurantId);

    List<OrderModel> listOrders(Long restaurantId, int status, int page, int size);
}
