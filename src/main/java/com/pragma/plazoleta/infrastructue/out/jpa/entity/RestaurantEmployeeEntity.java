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
@Table(name = "restaurant_employee")
@IdClass(RestaurantEmployeeEntityId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEmployeeEntity {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Id
    @Column(name = "restaurant_id")
    private Long restaurantId;
}
