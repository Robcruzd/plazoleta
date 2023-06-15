package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.domain.model.OrderModel;

public interface IValidateOrderWithEmployee {

    OrderModel validate(String token, Long orderId);
}
