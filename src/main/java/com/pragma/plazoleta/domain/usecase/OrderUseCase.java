package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.api.IOrderServicePort;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Long saveOrder(Long customerId, Long restaurantId) {
        return orderPersistencePort.saveOrder(customerId, restaurantId);
    }
}
