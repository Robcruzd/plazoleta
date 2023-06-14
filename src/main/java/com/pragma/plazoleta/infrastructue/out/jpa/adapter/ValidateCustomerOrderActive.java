package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.application.handler.IValidateCustomerOrderActive;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.security.jwt.IExtractAndValidateToken;
import com.pragma.plazoleta.infrastructue.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateCustomerOrderActive implements IValidateCustomerOrderActive {

    private final IExtractAndValidateToken extractAndValidateToken;
    private final IOrderRepository orderRepository;

    @Override
    public Long validate(String token) {
        Long customerId = extractAndValidateToken.extract(token);
        OrderEntity orderEntity = orderRepository.findFirstByCustomerIdOrderByIdDesc(customerId).orElse(null);
        if(orderEntity == null || orderEntity.getStatus().getId() == 4)
            return customerId;
        else
            throw new RequestException("The user can't request more orders, He has an order " +
                    "with status: "+orderEntity.getStatus().getName(), HttpStatus.BAD_REQUEST);
    }
}
