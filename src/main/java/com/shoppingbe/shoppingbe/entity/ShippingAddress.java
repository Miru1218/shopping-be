package com.shoppingbe.shoppingbe.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id;
    @Column(name = "order_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int orderId;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "full_Name")
    private String fullName;
    @Column(name = "postal_Code")
    private String postalCode;

}
