package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.model.Order;

public interface OrderService {

    Order getOrderMainByOrderId(int orderId) throws Exception;

    Order saveOrderMainByOrder(Order order) throws Exception;

    Order setupShippingAddress(Order order) throws Exception;

    void saveShippingAddress(Order order)throws Exception;

    Order setupOrderItems(Order order) throws Exception;

    void saveOrderDetails(Order order)throws Exception;
}
