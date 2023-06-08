package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.OrderModel;

public interface IOrderPersistencePort {

    Long saveOrder(Long customerId, Long restaurantId);
}
