package com.shoppingbe.shoppingbe.controller;

import com.google.gson.Gson;
import com.shoppingbe.shoppingbe.entity.OrderDetail;
import com.shoppingbe.shoppingbe.entity.OrderMain;
import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.entity.ShippingAddress;
import com.shoppingbe.shoppingbe.facade.OrderFacade;
import com.shoppingbe.shoppingbe.model.Order;
import com.shoppingbe.shoppingbe.model.OrderItem;
import com.shoppingbe.shoppingbe.repository.*;
import com.shoppingbe.shoppingbe.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@Tag(name = "OrderController")
public class OrderController {
    @Autowired
    UserDao userDao;
    @Autowired
    OrderMainDao orderMainDao;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    ShippingAddressDao shippingAddressDao;

    @Autowired
    OrderFacade orderFacade;


    @Operation(summary = "下訂單")
    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> order(@RequestBody Order order) {
        OrderMain orderMain = new OrderMain();
        orderMain.setItemsPrice(order.getItemsPrice());
        orderMain.setPaymentMethod(order.getPaymentMethod());
        orderMain.setShippingPrice(order.getShippingPrice());
        orderMain.setTaxPrice(order.getTaxPrice());
        orderMain.setTotalPrice(order.getTotalPrice());
        orderMain.setCreateTime(new Date());
        orderMain = orderMainDao.save(orderMain);
        order.setId(orderMain.getId());
        System.out.println(orderMain.getId());

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item : orderItems) {
            Optional<Product> optional = productDao.findById(item.getId());
            if (optional.isPresent()) {
                Product product = optional.get();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderMain.getId());
                orderDetail.setProductId(item.getId());
                orderDetail.setQty(item.getQuantity());
                int price = product.getPrice();
                orderDetail.setItemsPrice(price);
                double tax = price * 0.15;
                orderDetail.setTaxPrice(tax);
                orderDetail.setTotalPrice(price + tax);
                orderDetailDao.save(orderDetail);
            }
        }

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setOrderId(orderMain.getId());
        shippingAddress.setFullName(order.getShippingAddress().getFullName());
        shippingAddress.setAddress(order.getShippingAddress().getAddress());
        shippingAddress.setCity(order.getShippingAddress().getCity());
        shippingAddress.setPostalCode(order.getShippingAddress().getPostalCode());
        shippingAddress.setCountry(order.getShippingAddress().getCountry());
        shippingAddressDao.save(shippingAddress);

        System.out.println("測試資料    " + new Gson().toJson(order));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "訂單內容")
    @GetMapping(value = "/{orderId}")
    @ResponseBody
    public ResponseEntity<?> getOrderDetail(@PathVariable("orderId") Integer id) throws Exception {
        Order order = orderFacade.getOrderDetail(id);
        HttpStatus status = order.getId() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(order, status);
    }

}
