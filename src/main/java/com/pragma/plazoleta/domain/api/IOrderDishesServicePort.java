package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.OrderDishesModel;

import java.util.List;

public interface IOrderDishesServicePort {

    void saveOrderDishes(List<OrderDishesModel> orderDishesModel, Long orderId);

    List<OrderDishesModel> findOrderDishesByOrderId(Long orderId);
}
