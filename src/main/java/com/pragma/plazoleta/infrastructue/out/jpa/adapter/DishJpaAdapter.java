package com.pragma.plazoleta.infrastructue.out.jpa.adapter;

import com.pragma.plazoleta.domain.api.ICategoryServicePort;
import com.pragma.plazoleta.domain.api.IRestaurantServicePort;
import com.pragma.plazoleta.domain.model.CategoryModel;
import com.pragma.plazoleta.domain.model.DishModel;
import com.pragma.plazoleta.domain.model.RestaurantModel;
import com.pragma.plazoleta.domain.spi.IDishPersistencePort;
import com.pragma.plazoleta.infrastructue.exception.RequestException;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.CategoryEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.DishEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.entity.RestaurantEntity;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IDishEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.plazoleta.infrastructue.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryServicePort categoryServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        CategoryModel categoryModel = categoryServicePort.findCategoryById(dishModel.getCategoryModel().getId());
        dishModel.setCategoryModel(categoryModel);
        DishEntity dishEntity = this.dishEntityMapper.toDishEntity(dishModel);
        return this.dishEntityMapper.toDishModel(this.dishRepository.save(dishEntity));
    }

    @Override
    public DishModel findDishById(Long dishId) {
        DishEntity dishEntity = dishRepository.findById(dishId).orElseThrow(() -> new RequestException("El plato no fue encontrado", HttpStatus.NOT_FOUND));
        return dishEntityMapper.toDishModel(dishEntity);
    }

    @Override
    public List<DishModel> findDishesByRestaurantIdAndCategoryId(Long restaurantId, Long categoryId, int page, int size) {
        Sort sort  = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page-1, size, sort);
        RestaurantModel restaurantModel = restaurantServicePort.findRestaurantById(restaurantId);
        CategoryModel categoryModel = categoryServicePort.findCategoryById(categoryId);
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurantModel);
        CategoryEntity categoryEntity = categoryEntityMapper.toCategoryEntity(categoryModel);
        Page<DishEntity> dishEntities = dishRepository.findByRestaurantIdAndCategoryId(restaurantEntity, categoryEntity, pageable);
        return dishEntityMapper.toDishesModel(dishEntities);
    }
}
