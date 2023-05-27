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
import javax.persistence.Table;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "lastName", length = 50)
    private String address;

    @Column(name = "email", length = 50)
    private String urlLogo;

    @Column(name = "nit")
    private Long nit;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name = "owner_id")
    private Long ownerId;

}
