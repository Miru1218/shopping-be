package com.shoppingbe.shoppingbe.controller;


import com.shoppingbe.shoppingbe.facade.OrderFacade;
import com.shoppingbe.shoppingbe.model.Order;
import com.shoppingbe.shoppingbe.repository.OrderDetailDao;
import com.shoppingbe.shoppingbe.repository.OrderMainDao;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import com.shoppingbe.shoppingbe.repository.ShippingAddressDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
@Tag(name = "OrderController")
public class OrderController {
    @Resource(name = "orderMainDao")
    OrderMainDao orderMainDao;
    @Resource(name = "orderDetailDao")
    OrderDetailDao orderDetailDao;
    @Resource(name = "productDao")
    ProductDao productDao;
    @Resource(name = "shippingAddressDao")
    ShippingAddressDao shippingAddressDao;
    @Resource(name = "orderFacade")
    OrderFacade orderFacade;

    //    透過 @Operation 標記，可以對 API 進行簡介
    @Operation(summary = "下訂單")
    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Order> order(@RequestBody Order order) throws Exception {
        order = orderFacade.saveOrder(order);
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
