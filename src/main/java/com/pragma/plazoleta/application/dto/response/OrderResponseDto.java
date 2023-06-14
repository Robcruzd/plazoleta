package com.pragma.plazoleta.application.dto.response;

import com.pragma.plazoleta.application.dto.request.OrderDishesRequestDto;
import com.pragma.plazoleta.domain.model.StatusOrderModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long customerId;
    private LocalDate date;
    private StatusOrderModel status;
    private Long employeeId;
    private Long restaurantId;
    private List<OrderDishesResponseDto> orderDishesResponseDtoList;
}
