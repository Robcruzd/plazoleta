package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;

import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Long saveOrder(Long customerId, Long restaurantId) {
        return orderPersistencePort.saveOrder(customerId, restaurantId);
    }

    @Override
    public List<OrderModel> listOrders(Long restaurantId, int status, int page, int size) {
        return orderPersistencePort.listOrders(restaurantId, status, page, size);
    }
}
