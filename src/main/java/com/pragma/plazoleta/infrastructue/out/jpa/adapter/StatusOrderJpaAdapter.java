package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.StatusOrderModel;
import com.pragma.plazoleta.domain.spi.IStatusOrderPersistencePort;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IStatusOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IStatusOrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusOrderJpaAdapter implements IStatusOrderPersistencePort {

    private final IStatusOrderRepository statusOrderRepository;
    private final IStatusOrderEntityMapper statusOrderEntityMapper;
    @Override
    public StatusOrderModel findStatusOrderById(int statusId) {
        StatusOrderEntity statusOrderEntity = statusOrderRepository.findById(statusId).orElse(null);
        return statusOrderEntityMapper.toModel(statusOrderEntity);
    }
}
