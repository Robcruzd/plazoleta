package com.pragma.plazoleta.infrastructue.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.StatusOrderModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.StatusOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IStatusOrderEntityMapper {

    StatusOrderModel toModel(StatusOrderEntity statusOrderEntity);
}
