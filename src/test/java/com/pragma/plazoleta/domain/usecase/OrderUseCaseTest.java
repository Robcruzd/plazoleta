package com.pragma.plazoleta.domain.usecase;

import com.pragma.plazoleta.domain.spi.IOrderPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    void saveOrder() {
        Long customerId = 1L;
        Long restaurantId = 2L;
        Long orderId = 3L;

        when(orderPersistencePort.saveOrder(customerId, restaurantId)).thenReturn(orderId);

        Long result = orderUseCase.saveOrder(customerId, restaurantId);

        assertEquals(orderId, result);
    }
}