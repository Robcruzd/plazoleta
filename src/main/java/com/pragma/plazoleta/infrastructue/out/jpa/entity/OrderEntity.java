package com.pragma.plazoleta.infrastructue.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "status")
    private StatusOrderEntity status;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "security_pin", length = 9)
    private String securityPin;
}
