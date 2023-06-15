package com.pragma.plazoleta.infrastructue.out.jpa.mapper;

import com.pragma.plazoleta.application.dto.response.OrderUpdateResponseDto;
import com.pragma.plazoleta.domain.model.OrderModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {

    OrderEntity toEntity(OrderModel orderModel);

    List<OrderModel> toModelList(Page<OrderEntity> orderEntityList);

    List<OrderUpdateResponseDto> toResponseDto(List<OrderEntity> orderEntityList);

    List<OrderEntity> toEntityList(List<OrderModel> orderModelList);

    OrderModel toModel(OrderEntity orderEntity);
}
