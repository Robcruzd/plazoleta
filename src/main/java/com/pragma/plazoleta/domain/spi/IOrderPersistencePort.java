package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;

import java.util.List;

public interface IOrderPersistencePort {

    Long saveOrder(Long customerId, Long restaurantId);

    List<OrderModel> listOrders(Long restaurantId, int status, int page, int size);

    void updateOrder(Long userId, List<OrderModel> orderModelList, int statusId);
}
