package com.shoppingbe.shoppingbe.facade;

import com.google.gson.Gson;
import com.shoppingbe.shoppingbe.model.Order;
import com.shoppingbe.shoppingbe.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderFacade {
    private OrderService orderService;

    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }


    public Order getOrderDetail(int orderId) throws Exception {
        Order order = orderService.getOrderMainByOrderId(orderId);
        orderService.setupShippingAddress(order);
        orderService.setupOrderItems(order);
        return order;
    }

    public Order saveOrder(Order order) throws Exception {
        order = orderService.saveOrderMainByOrder(order);
        orderService.saveOrderDetails(order);
        orderService.saveShippingAddress(order);
        System.out.println("測試資料    " + new Gson().toJson(order));
        return order;
    }
}
