package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.domain.model.OrderModel;
import org.springframework.lang.Nullable;

public interface IValidateOrderWithEmployee {

    OrderModel validate(String token, Long orderId, @Nullable String securityPin);
}
