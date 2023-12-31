package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.response.OrderResponseDto;
import com.pragma.plazoleta.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {

    List<OrderResponseDto> toOrderDto(List<OrderModel> orderModelList);
}
