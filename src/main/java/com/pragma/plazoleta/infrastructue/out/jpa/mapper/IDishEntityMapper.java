package com.pragma.plazoleta.infrastructue.out.jpa.mapper;

import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

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

    @Mapping(source = "dishEntity.restaurantId.id", target = "restaurantModel.id")
    @Mapping(source = "dishEntity.categoryId.id", target = "categoryModel.id")
    List<DishModel> toDishesModel(Page<DishEntity> dishEntities);
}
