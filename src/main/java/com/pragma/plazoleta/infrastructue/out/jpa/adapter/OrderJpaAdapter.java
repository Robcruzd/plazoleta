package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IStatusOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IStatusOrderRepository statusOrderRepository;
    @Override
    public Long saveOrder(Long customerId, Long restaurantId) {
        int statusOrderId = 1;
        StatusOrderEntity statusOrderEntity = statusOrderRepository.findById(statusOrderId).orElseThrow(
                ()->new RequestException("Estado no encontrado", HttpStatus.NOT_FOUND)
        );
        OrderEntity orderEntity = new OrderEntity(null, customerId, LocalDate.now(), statusOrderEntity, null, restaurantId);
        return orderRepository.save(orderEntity).getId();
    }
}
