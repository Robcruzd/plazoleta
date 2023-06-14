package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderUpdateResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateOrderWithRestaurantTest {

    @InjectMocks
    private ValidateOrderWithRestaurant validateOrderWithRestaurant;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;


    @Test
    public void testValidate_WithValidOrders_ReturnsOrderUpdateResponseDtoList() {
        Long restaurantId = 123L;

        List<UpdateOrderRequestDto> updateOrderRequestDtoList = new ArrayList<>();

        Iterable<Long> iterable = updateOrderRequestDtoList.stream()
                .map(UpdateOrderRequestDto::getId)
                .collect(Collectors.toList());

        List<OrderEntity> orderEntityList = new ArrayList<>();

        when(orderRepository.findAllById(iterable)).thenReturn(orderEntityList);
        when(orderEntityMapper.toResponseDto(orderEntityList)).thenReturn(new ArrayList<>());

        List<OrderUpdateResponseDto> result = validateOrderWithRestaurant.validate(updateOrderRequestDtoList, restaurantId);

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(orderRepository, times(1)).findAllById(iterable);
        verify(orderEntityMapper, times(1)).toResponseDto(orderEntityList);
    }

}