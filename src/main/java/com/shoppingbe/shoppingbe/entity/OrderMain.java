package com.shoppingbe.shoppingbe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_main")
@Getter
@Setter
@NoArgsConstructor
public class OrderMain {
    @Id
    @Column(name = "id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    @Column(name = "items_price")
    private int itemsPrice;
    @Column(name = "payment_method",columnDefinition = "text")
    private String paymentMethod;
    @Column(name = "shipping_price")
    private double shippingPrice;
    @Column(name = "tax_price")
    private double taxPrice;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "created_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "delivered_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveredAt;
    @Column(name = "paid_at")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date paidAt;
    @Column(name = "isDelivered")
    private boolean isDelivered;
    @Column(name = "isPaid")
    private boolean isPaid;
    @Transient
    private List<Product> orderItems;
    @Transient
    private ShippingAddress shippingAddress;
    @Transient
    private String userAccount;
}
