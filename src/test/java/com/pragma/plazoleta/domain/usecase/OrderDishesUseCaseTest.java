package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.model.OrderDishesModel;
import com.pragma.plazoleta.domain.spi.IOrderDishesPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderDishesUseCaseTest {

    @Mock
    private IOrderDishesPersistencePort orderDishesPersistencePort;

    @InjectMocks
    private OrderDishesUseCase orderDishesUseCase;

    @Test
    void saveOrderDishes() {
        List<OrderDishesModel> orderDishesModelList = new ArrayList<>();
        Long orderId = 1L;

        orderDishesUseCase.saveOrderDishes(orderDishesModelList, orderId);

        verify(orderDishesPersistencePort, times(1)).saveOrderDishes(orderDishesModelList, orderId);
    }
}