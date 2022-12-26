package com.shoppingbe.shoppingbe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_main")
@Getter
@Setter
@NoArgsConstructor
public class OrderMain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "items_price")
    private int itemsPrice;
    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "shipping_price")
    private double shippingPrice;
    @Column(name = "tax_price")
    private double taxPrice;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
}
