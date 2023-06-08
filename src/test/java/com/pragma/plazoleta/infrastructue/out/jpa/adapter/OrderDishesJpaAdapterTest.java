package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.OrderDishesModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderDishesEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderDishesEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderDishesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDishesJpaAdapterTest {

    @Mock
    private IOrderDishesRepository orderDishesRepository;

    @Mock
    private IOrderDishesEntityMapper orderDishesEntityMapper;

    @InjectMocks
    private OrderDishesJpaAdapter orderDishesJpaAdapter;

    @BeforeEach
    public void setup() {
        when(orderDishesEntityMapper.toEntityList(anyList())).thenReturn(Collections.emptyList());
    }

    @Test
    void saveOrderDishes() {
        Long orderId = 1L;
        List<OrderDishesModel> orderDishesModelList = Collections.singletonList(new OrderDishesModel());

        // Mocking the mapper
        List<OrderDishesEntity> orderDishesEntityList = Collections.singletonList(new OrderDishesEntity());
        when(orderDishesEntityMapper.toEntityList(orderDishesModelList)).thenReturn(orderDishesEntityList);

        // Invoke the method
        orderDishesJpaAdapter.saveOrderDishes(orderDishesModelList, orderId);

        // Verify the interactions
        verify(orderDishesEntityMapper, times(1)).toEntityList(orderDishesModelList);
        verify(orderDishesRepository, times(1)).saveAll(orderDishesEntityList);
    }
}