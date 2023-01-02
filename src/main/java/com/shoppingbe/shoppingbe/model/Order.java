package com.shoppingbe.shoppingbe.model;

import com.shoppingbe.shoppingbe.entity.ShippingAddress;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private Integer id;
    private List<OrderItem> orderItems;
    private ShippingAddress shippingAddress;
    private String paymentMethod;
    private Integer itemsPrice;
    private Double shippingPrice;
    private Double taxPrice;
    private Double totalPrice;
    private String deliveredAt;
    private String paidAt;
    private boolean isDelivered;
    private boolean isPaid;
    private String userAccount;
}
