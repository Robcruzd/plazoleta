package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.OrderModel;

import java.util.List;

public interface IOrderPersistencePort {

    Long saveOrder(Long customerId, Long restaurantId);

    List<OrderModel> listOrders(Long restaurantId, int status, int page, int size);
}
