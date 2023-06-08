package com.pragma.plazoleta.infrastructue.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "order_dishes")
@IdClass(OrderDishesEntityId.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDishesEntity {

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @Id
    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "amount")
    private int amount;
}
