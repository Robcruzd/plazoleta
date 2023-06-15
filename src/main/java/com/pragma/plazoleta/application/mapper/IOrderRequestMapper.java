package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.OrderDishesRequestDto;
import com.pragma.plazoleta.application.dto.request.UpdateOrderRequestDto;
import com.pragma.plazoleta.application.dto.response.OrderUpdateResponseDto;
import com.pragma.plazoleta.domain.model.OrderDishesModel;
import com.pragma.plazoleta.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    List<OrderDishesModel> toModel(List<OrderDishesRequestDto> orderDishesRequestDto);

    List<OrderModel> toOrderModelList(List<OrderUpdateResponseDto> orderUpdateResponseDtoList);

    OrderModel toOrderModel(UpdateOrderRequestDto updateOrderRequestDto);
}
