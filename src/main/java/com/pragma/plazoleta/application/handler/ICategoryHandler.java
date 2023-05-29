package com.pragma.plazoleta.application.handler;

import com.pragma.plazoleta.application.dto.request.CategoryRequestDto;

public interface ICategoryHandler {

    CategoryRequestDto findCategoryById(Long categoryId);
}
