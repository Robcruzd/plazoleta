package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.StatusOrderModel;

public interface IStatusOrderPersistencePort {

    StatusOrderModel findStatusOrderById(int statusId);
}
