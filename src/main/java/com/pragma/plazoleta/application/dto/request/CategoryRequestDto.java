package com.pragma.plazoleta.application.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {

    private Long id;
    private String name;
    private String description;
}
