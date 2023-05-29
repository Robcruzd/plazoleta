package com.pragma.plazoleta.application.mapper;

import com.pragma.plazoleta.application.dto.request.CategoryRequestDto;
import com.pragma.plazoleta.domain.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {

    CategoryModel toCategoryModel(CategoryRequestDto categoryRequestDto);
    CategoryRequestDto toCategoryDto(CategoryModel categoryModel);
}
