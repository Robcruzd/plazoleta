package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderUpdateResponseDto;
import com.pragma.plazoleta.application.exception.ApplicationException;
import com.pragma.plazoleta.application.handler.IValidateOrderWithRestaurant;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidateOrderWithRestaurant implements IValidateOrderWithRestaurant {

    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public List<OrderUpdateResponseDto> validate(List<UpdateOrderRequestDto> updateOrderRequestDtoList, Long restaurantId) {
        Iterable<Long> iterable = updateOrderRequestDtoList.stream()
                .map(UpdateOrderRequestDto::getId)
                .collect(Collectors.toList());
        List<OrderEntity> orderEntityList = orderRepository.findAllById(iterable);
        for (OrderEntity orderEntity: orderEntityList) {
            if(orderEntity.getRestaurantId() != restaurantId)
                throw new ApplicationException("The employee can not access to the order");
        }
        return orderEntityMapper.toResponseDto(orderEntityList);
    }
}
