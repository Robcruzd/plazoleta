package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryServicePort categoryServicePort;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        CategoryModel categoryModel = categoryServicePort.findCategoryById(dishModel.getCategoryModel().getId());
        dishModel.setCategoryModel(categoryModel);
        DishEntity dishEntity = this.dishEntityMapper.toDishEntity(dishModel);
        return this.dishEntityMapper.toDishModel(this.dishRepository.save(dishEntity));
    }

    @Override
    public void updateDish(DishModel dishModel) {

    }
}
