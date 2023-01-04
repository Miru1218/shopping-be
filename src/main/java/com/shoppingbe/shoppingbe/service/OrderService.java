package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.OrderMain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    OrderMain getOrderMainByOrderId(int orderId) throws Exception;

    OrderMain saveOrderMain(OrderMain order, HttpServletRequest rq) throws Exception;

    OrderMain setupShippingAddress(OrderMain order) throws Exception;

    void saveShippingAddress(OrderMain order)throws Exception;

    OrderMain setupOrderItems(OrderMain order) throws Exception;

    void saveOrderDetails(OrderMain order)throws Exception;

    List<OrderMain> findOrderMainsByUserId(int userId) throws Exception;
}
