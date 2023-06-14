package com.pragma.plazoleta.infrastructue.out.jpa.mapper;

import com.pragma.plazoleta.application.dto.request.RestaurantEmployeeRequestDto;
import com.pragma.plazoleta.domain.model.RestaurantEmployeeModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantEmployeeMapper {

    @Mapping(source = "restaurantEmployeeModel.userId", target = "employeeId")
    RestaurantEmployeeEntity toEntity(RestaurantEmployeeModel restaurantEmployeeModel);

    @Mapping(source = "restaurantEmployeeEntity.employeeId", target = "userId")
    RestaurantEmployeeRequestDto toDto(RestaurantEmployeeEntity restaurantEmployeeEntity);
}
