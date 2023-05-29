package com.pragma.plazoleta.infrastructue.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryEntityMapper {


    CategoryEntity toCategoryEntity(CategoryModel categoryModel);
    CategoryModel toCategoryModel(CategoryEntity category);
}
