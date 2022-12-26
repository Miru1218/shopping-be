package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.model.Order;

import java.security.PrivateKey;

public interface OrderService {
    Order getOrderMainByOrderId(int orderId) throws Exception;

    Order setupShippingAddress(Order order) throws Exception;

    Order setupOrderItems(Order order) throws Exception;
}
