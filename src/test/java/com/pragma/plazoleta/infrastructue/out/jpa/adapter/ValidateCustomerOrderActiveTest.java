package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IOrderRepository;
import com.pragma.plazoleta.infrastructue.security.jwt.IExtractAndValidateToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateCustomerOrderActiveTest {
    @Mock
    private IExtractAndValidateToken extractAndValidateToken;

    @Mock
    private IOrderRepository orderRepository;

    @InjectMocks
    private ValidateCustomerOrderActive validateCustomerOrderActive;

    @Test
    void validate() {
        String token = "valid_token";
        Long customerId = 1L;

        when(extractAndValidateToken.extract(token)).thenReturn(customerId);

        when(orderRepository.findFirstByCustomerIdOrderByIdDesc(customerId)).thenReturn(Optional.empty());

        Long result = validateCustomerOrderActive.validate(token);

        assertEquals(customerId, result);
    }

    @Test
    public void validate_CustomerOrderStatusNotAllowed() {
        String token = "valid_token";
        Long customerId = 1L;

        when(extractAndValidateToken.extract(token)).thenReturn(customerId);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(new StatusOrderEntity(2, "En_proceso"));
        when(orderRepository.findFirstByCustomerIdOrderByIdDesc(customerId)).thenReturn(Optional.of(orderEntity));

        assertThrows(RequestException.class, () -> validateCustomerOrderActive.validate(token));
    }
}