package com.shoppingbe.shoppingbe.facade;

import com.google.gson.Gson;
import com.shoppingbe.shoppingbe.entity.OrderMain;
import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.service.OrderService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Service
public class OrderFacade {
    private OrderService orderService;

    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }


    public OrderMain getOrderDetail(UUID orderId) throws Exception {
        OrderMain order = orderService.getOrderMainByOrderId(orderId);
        if(orderId.toString().equalsIgnoreCase(order.getId().toString())) {
            orderService.setupShippingAddress(order);
            orderService.setupOrderItems(order);
        }
        return order;
    }

    public OrderMain saveOrder(OrderMain order, HttpServletRequest rq) throws Exception {
        order.setId(UUID.randomUUID());
        orderService.saveOrderMain(order,rq);
        orderService.saveOrderDetails(order);
        orderService.saveShippingAddress(order);
        System.out.println("測試資料    " + new Gson().toJson(order));
        return order;
    }

    public List<OrderMain> getHistoryOrders(HttpServletRequest rq) throws Exception{
        HttpSession session = rq.getSession();
        User user = (User)session.getAttribute("UserSession");
        List<OrderMain> orderList = orderService.findOrderMainsByUserId(user.getId());
        return orderList;
    }
}
