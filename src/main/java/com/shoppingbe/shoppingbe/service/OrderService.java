package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.OrderMain;
import com.shoppingbe.shoppingbe.model.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {

    Order getOrderMainByOrderId(int orderId) throws Exception;

    Order saveOrderMainByOrder(Order order, HttpServletRequest rq) throws Exception;

    Order setupShippingAddress(Order order) throws Exception;

    void saveShippingAddress(Order order)throws Exception;

    Order setupOrderItems(Order order) throws Exception;

    void saveOrderDetails(Order order)throws Exception;

    List<OrderMain> findOrderMainsByUserId(int userId) throws Exception;
}
