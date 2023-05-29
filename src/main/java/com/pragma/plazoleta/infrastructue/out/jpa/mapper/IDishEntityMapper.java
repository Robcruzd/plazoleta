package com.pragma.plazoleta.infrastructue.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {

    @Mapping(source = "dishModel.categoryModel", target = "categoryId")
    @Mapping(source = "dishModel.restaurantModel", target = "restaurantId")
    DishEntity toDishEntity(DishModel dishModel);

    @Mapping(source = "dishEntity.restaurantId.id", target = "restaurantModel.id")
    @Mapping(source = "dishEntity.categoryId.id", target = "categoryModel.id")
    DishModel toDishModel(DishEntity dishEntity);
}
