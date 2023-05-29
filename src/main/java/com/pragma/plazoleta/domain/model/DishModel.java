package com.pragma.plazoleta.domain.model;

import com.pragma.plazoleta.domain.exception.DomainException;

public class DishModel {

    private Long id;
    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private CategoryModel categoryModel;
    private RestaurantModel restaurantModel;
    private boolean active;

    public DishModel() {
    }

    public DishModel(Long id, String name, int price, String description, String imageUrl, CategoryModel categoryModel, RestaurantModel restaurantModel, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryModel = categoryModel;
        this.restaurantModel = restaurantModel;
        this.active = active;
    }

    public void validate() {
        validateRequiredFields();
        validatePrice();
    }

    private void validateRequiredFields() {
        if (name == null || price == 0 || description == null || imageUrl == null || categoryModel == null || restaurantModel == null) {
            throw new DomainException("Todos los campos son obligatorios");
        }
    }

    private void validatePrice() {
        if (price <= 0) {
            throw new DomainException("El precio debe ser un número entero positivo.");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public RestaurantModel getRestaurantModel() {
        return restaurantModel;
    }

    public void setRestaurantModel(RestaurantModel restaurantModel) {
        this.restaurantModel = restaurantModel;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
