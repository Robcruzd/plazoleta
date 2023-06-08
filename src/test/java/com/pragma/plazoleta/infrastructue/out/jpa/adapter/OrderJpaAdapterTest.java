package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IStatusOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderJpaAdapterTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IStatusOrderRepository statusOrderRepository;

    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;

    @Test
    public void testSaveOrder_Success() {
        Long customerId = 1L;
        Long restaurantId = 2L;
        int statusOrderId = 1;

        StatusOrderEntity statusOrderEntity = new StatusOrderEntity();
        statusOrderEntity.setId(statusOrderId);
        when(statusOrderRepository.findById(statusOrderId)).thenReturn(Optional.of(statusOrderEntity));

        OrderEntity savedOrderEntity = new OrderEntity();
        savedOrderEntity.setId(1L);
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(savedOrderEntity);

        Long orderId = orderJpaAdapter.saveOrder(customerId, restaurantId);

        verify(statusOrderRepository, times(1)).findById(statusOrderId);
        verify(orderRepository, times(1)).save(any(OrderEntity.class));

        assertNotNull(orderId);
        assertEquals(savedOrderEntity.getId(), orderId);
    }

    @Test
    public void testSaveOrder_StatusOrderNotFound() {
        Long customerId = 1L;
        Long restaurantId = 2L;
        int statusOrderId = 1;

        when(statusOrderRepository.findById(statusOrderId)).thenReturn(Optional.empty());

        assertThrows(RequestException.class, () -> orderJpaAdapter.saveOrder(customerId, restaurantId));

        verify(statusOrderRepository, times(1)).findById(statusOrderId);
        verify(orderRepository, never()).save(any(OrderEntity.class));
    }
}