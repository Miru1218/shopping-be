package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.OrderMain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderMain getOrderMainByOrderId(UUID orderId) throws Exception;

    void saveOrderMain(OrderMain order, HttpServletRequest rq) throws Exception;

    OrderMain setupShippingAddress(OrderMain order) throws Exception;

    void saveShippingAddress(OrderMain order) throws Exception;

    OrderMain setupOrderItems(OrderMain order) throws Exception;

    void saveOrderDetails(OrderMain order) throws Exception;

    List<OrderMain> findOrderMainsByUserId(int userId) throws Exception;

    OrderMain setupPay(OrderMain order) throws Exception;

    OrderMain setupCancelItems(UUID orderId) throws Exception;

    OrderMain setupCancelSingle(OrderMain order) throws Exception;
}
