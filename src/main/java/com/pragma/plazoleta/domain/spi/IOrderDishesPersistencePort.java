package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.OrderDishesModel;

import java.util.List;

public interface IOrderDishesPersistencePort {

    void saveOrderDishes(List<OrderDishesModel> orderDishesModel, Long orderId);
}
