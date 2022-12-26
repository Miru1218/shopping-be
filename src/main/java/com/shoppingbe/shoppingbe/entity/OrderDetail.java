package com.shoppingbe.shoppingbe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "qty")
    private int qty;
    @Column(name = "items_price")
    private int itemsPrice;
    @Column(name = "tax_price")
    private double taxPrice;
    @Column(name = "total_price")
    private double totalPrice;
}
