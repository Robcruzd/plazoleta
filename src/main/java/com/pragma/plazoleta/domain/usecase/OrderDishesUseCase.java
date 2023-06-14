package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IOrderDishesServicePort;
import com.pragma.plazoleta.domain.model.OrderDishesModel;
import com.pragma.plazoleta.domain.spi.IOrderDishesPersistencePort;

import java.util.List;


public class OrderDishesUseCase implements IOrderDishesServicePort {
    private final IOrderDishesPersistencePort orderDishesPersistencePort;

    public OrderDishesUseCase(IOrderDishesPersistencePort orderDishesPersistencePort) {
        this.orderDishesPersistencePort = orderDishesPersistencePort;
    }

    @Override
    public void saveOrderDishes(List<OrderDishesModel> orderDishesModel, Long orderId) {
        orderDishesPersistencePort.saveOrderDishes(orderDishesModel, orderId);
    }

    @Override
    public List<OrderDishesModel> findOrderDishesByOrderId(Long orderId) {
        return orderDishesPersistencePort.findOrderDishesByOrderId(orderId);
    }
}
