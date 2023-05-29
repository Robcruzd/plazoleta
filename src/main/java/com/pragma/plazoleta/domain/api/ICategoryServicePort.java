package com.pragma.plazoleta.domain.api;

import com.pragma.plazoleta.domain.model.CategoryModel;

public interface ICategoryServicePort {

    CategoryModel findCategoryById(Long categoryId);
}
