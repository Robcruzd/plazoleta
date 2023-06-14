package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IStatusOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @Mock
    private IOrderEntityMapper orderEntityMapper;

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

    @Test
    public void testUpdateOrder() {
        // Mock de los datos de entrada
        Long userId = 1L;
        List<OrderModel> orderModelList = new ArrayList<>();
        int statusId = 1;

        // Mock del repository de statusOrder
        StatusOrderEntity statusOrderEntity = new StatusOrderEntity();
        when(statusOrderRepository.findById(statusId)).thenReturn(Optional.of(statusOrderEntity));

        // Mock del mapper de orderEntity
        List<OrderEntity> orderEntityList = new ArrayList<>();
        when(orderEntityMapper.toEntityList(orderModelList)).thenReturn(orderEntityList);

        // Llamada al método que se va a probar
        orderJpaAdapter.updateOrder(userId, orderModelList, statusId);

        // Verificaciones
        verify(orderRepository, times(1)).saveAll(orderEntityList);
    }



}