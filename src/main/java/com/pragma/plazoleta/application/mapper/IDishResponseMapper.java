package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.DishRequestDto;
import com.pragma.plazoleta.application.dto.response.DishResponseDto;
import com.pragma.plazoleta.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {

    @Mapping(source = "dishResponseDto.categoryRequestDto", target = "categoryModel")
    @Mapping(source = "dishResponseDto.restaurantRequestDto", target = "restaurantModel")
    DishModel toDishModel(DishResponseDto dishResponseDto);

    @Mapping(source = "dishRequestDto.categoryRequestDto", target = "categoryRequestDto.id")
    @Mapping(source = "dishRequestDto.restaurantRequestDto", target = "restaurantRequestDto.id")
    DishResponseDto toDishResDto(DishRequestDto dishRequestDto);
}
