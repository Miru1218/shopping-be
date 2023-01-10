package com.shoppingbe.shoppingbe.controller;


import com.google.gson.Gson;
import com.shoppingbe.shoppingbe.entity.OrderMain;
import com.shoppingbe.shoppingbe.facade.OrderFacade;
import com.shoppingbe.shoppingbe.repository.OrderDetailDao;
import com.shoppingbe.shoppingbe.repository.OrderMainDao;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import com.shoppingbe.shoppingbe.repository.ShippingAddressDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Value("${paypal.client.id}")
    private String paypalClientId;

    //    透過 @Operation 標記，可以對 API 進行簡介
    @Operation(summary = "下訂單")
    @PostMapping(value = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<OrderMain> order(@RequestBody OrderMain order, HttpServletRequest rq) throws Exception {
        order = orderFacade.saveOrder(order, rq);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Operation(summary = "訂單內容")
    @GetMapping(value = "/{orderId}")
    @ResponseBody
    public ResponseEntity<OrderMain> getOrderDetail(@PathVariable("orderId") String id) throws Exception {
        OrderMain order = orderFacade.getOrderDetail(UUID.fromString(id));
        HttpStatus status = id.equalsIgnoreCase(order.getId().toString()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(order, status);
    }

    @Operation(summary = "歷史訂單")
    @GetMapping(value = "/mine")
    @ResponseBody
    public ResponseEntity<List<OrderMain>> getHistoryOrders(HttpServletRequest rq) throws Exception {
        List<OrderMain> orderList = orderFacade.getHistoryOrders(rq);
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @Operation(summary = "paypal")
    @GetMapping(value = "/keys/paypal")
    @ResponseBody
    public ResponseEntity<String> getPaypalApi() throws Exception {
        return new ResponseEntity<>(paypalClientId, HttpStatus.OK);
    }

    @Operation(summary = "paypal")
    @PutMapping(value = "/{orderId}/pay")
    @ResponseBody
    public ResponseEntity<OrderMain> putPay(@PathVariable("orderId") String id) throws Exception {
        OrderMain order = orderFacade.getOrderDetail(UUID.fromString(id));
        order = orderFacade.setupPay(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
