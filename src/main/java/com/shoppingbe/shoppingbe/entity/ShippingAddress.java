package com.shoppingbe.shoppingbe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shipping_address")
@Getter
@Setter
@NoArgsConstructor
public class ShippingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "full_Name")
    private String fullName;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "postal_Code")
    private String postalCode;
    @Column(name = "country")
    private String country;
}
