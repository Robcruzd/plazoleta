package com.pragma.plazoleta.domain.model;

import java.time.LocalDate;
import java.util.Date;

public class OrderModel {

    private Long id;
    private Long customerId;
    private LocalDate date;
    private StatusOrderModel status;
    private Long employeeId;
    private Long restaurantId;

    public OrderModel() {
    }

    public OrderModel(Long id, Long customerId, LocalDate date, StatusOrderModel status, Long employeeId, Long restaurantId) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.status = status;
        this.employeeId = employeeId;
        this.restaurantId = restaurantId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StatusOrderModel getStatus() {
        return status;
    }

    public void setStatus(StatusOrderModel status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}
