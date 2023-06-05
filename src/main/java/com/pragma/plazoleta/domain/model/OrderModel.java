package com.pragma.plazoleta.domain.model;

import java.util.Date;

public class OrderModel {

    private Long id;
    private Long customerId;
    private Date date;
    private boolean status;
    private Long chefId;
    private RestaurantModel restaurant;

    public OrderModel() {
    }

    public OrderModel(Long id, Long customerId, Date date, boolean status, Long chefId, RestaurantModel restaurant) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.status = status;
        this.chefId = chefId;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getChefId() {
        return chefId;
    }

    public void setChefId(Long chefId) {
        this.chefId = chefId;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }
}
