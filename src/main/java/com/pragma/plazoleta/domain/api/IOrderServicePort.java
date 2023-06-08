package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.OrderModel;

public interface IOrderServicePort {

    Long saveOrder(Long customerId, Long restaurantId);
}
