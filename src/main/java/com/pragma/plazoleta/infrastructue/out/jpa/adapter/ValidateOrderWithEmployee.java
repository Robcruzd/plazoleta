package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.application.handler.IValidateOrderWithEmployee;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.security.jwt.IExtractAndValidateToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ValidateOrderWithEmployee implements IValidateOrderWithEmployee {

    private final IExtractAndValidateToken extractAndValidateToken;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public OrderModel validate(String token, Long orderId) {
        try {
            Long employeeId = extractAndValidateToken.extract(token);
            OrderEntity orderEntity = orderRepository.findById(orderId).orElse(null);
            if (orderEntity == null || !Objects.equals(orderEntity.getEmployeeId(), employeeId))
                throw new RequestException("employee validation failed", HttpStatus.BAD_REQUEST);
            return orderEntityMapper.toModel(orderEntity);
        } catch (Exception e) {
            throw new RequestException("employee validation failed", HttpStatus.BAD_REQUEST);
        }

    }
}
