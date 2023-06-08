package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.OrderDishesModel;
import com.pragma.plazoleta.domain.spi.IOrderDishesPersistencePort;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderDishesEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderDishesEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderDishesRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderDishesJpaAdapter implements IOrderDishesPersistencePort {

    private final IOrderDishesRepository orderDishesRepository;
    private final IOrderDishesEntityMapper orderDishesEntityMapper;
    @Override
    public void saveOrderDishes(List<OrderDishesModel> orderDishesModel, Long orderId) {
        List<OrderDishesEntity> orderDishesEntityList = orderDishesEntityMapper.toEntityList(orderDishesModel);
        for (OrderDishesEntity orderDishesEntity: orderDishesEntityList) {
            orderDishesEntity.setOrderId(orderId);
        }
        orderDishesRepository.saveAll(orderDishesEntityList);
    }
}
