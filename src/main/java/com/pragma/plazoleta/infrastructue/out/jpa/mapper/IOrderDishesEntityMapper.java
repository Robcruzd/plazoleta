package com.pragma.plazoleta.infrastructue.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.OrderDishesModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.OrderDishesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishesEntityMapper {

    List<OrderDishesEntity> toEntityList(List<OrderDishesModel> orderDishesModelList);
}
