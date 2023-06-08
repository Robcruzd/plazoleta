package com.pragma.plazoleta.domain.model;

public class OrderDishesModel {

    private Long orderId;
    private Long dishId;
    private int amount;

    public OrderDishesModel() {
    }

    public OrderDishesModel(Long orderId, Long dishId, int amount) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
