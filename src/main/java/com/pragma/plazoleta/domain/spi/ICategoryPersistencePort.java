package com.pragma.plazoleta.domain.spi;

import com.pragma.plazoleta.domain.model.CategoryModel;

public interface ICategoryPersistencePort {

    CategoryModel findCategoryById(Long categoryId);
}
