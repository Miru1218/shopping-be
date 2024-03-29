package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.*;
import com.shoppingbe.shoppingbe.repository.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Service
public class OrderServiceImpl implements OrderService {
    private OrderMainDao orderMainDao;
    private ShippingAddressDao shippingAddressDao;
    private OrderDetailDao orderDetailDao;

    private ProductDao productDao;
    private final UserDao userDao;

    public OrderServiceImpl(OrderMainDao orderMainDao, ShippingAddressDao shippingAddressDao,
                            OrderDetailDao orderDetailDao, ProductDao productDao, UserDao userDao) {
        this.orderMainDao = orderMainDao;
        this.shippingAddressDao = shippingAddressDao;
        this.orderDetailDao = orderDetailDao;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    public OrderMain getOrderMainByOrderId(UUID orderId) throws Exception {
        OrderMain order = new OrderMain();
        Optional<OrderMain> orderMainOptional = orderMainDao.findById(orderId);
        if (orderMainOptional.isPresent()) {
            order = orderMainOptional.get();
        }
        return order;
    }

    @Override
    public void saveOrderMain(OrderMain order, HttpServletRequest rq) throws Exception {
        HttpSession session = rq.getSession();
        User user = (User) session.getAttribute("UserSession");
        order.setCreatedAt(new Date());
        order.setUserId(user.getId());
        //        System.out.println(new Gson().toJson(order));
        orderMainDao.save(order);
    }

    @Override
    public OrderMain setupShippingAddress(OrderMain order) throws Exception {
        List<ShippingAddress> shippingAddressList = shippingAddressDao.findByOrderId(order.getId());
        ShippingAddress shippingAddress = shippingAddressList.get(0);
        order.setShippingAddress(shippingAddress);
        return order;
    }

    @Override
    public void saveShippingAddress(OrderMain order) throws Exception {
        ShippingAddress shippingAddress = order.getShippingAddress();
        shippingAddress.setOrderId(order.getId());
        shippingAddressDao.save(shippingAddress);
    }

    @Override
    public OrderMain setupOrderItems(OrderMain order) throws Exception {
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(order.getId());
        orderDetails.sort(Comparator.comparing(OrderDetail::getId).reversed());
        List<Product> orderItems = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            Product orderItem = new Product();
            orderItem.setId(orderDetail.getProductId());
            Optional<Product> productOptional = productDao.findById(orderDetail.getProductId());
            Product product = productOptional.get();
            orderItem.setName(product.getName());
            orderItem.setSlug(product.getSlug());
            orderItem.setImage(product.getImage());
            orderItem.setPrice(orderDetail.getPrice());
            orderItem.setQuantity(orderDetail.getQty());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public void saveOrderDetails(OrderMain order) throws Exception {
        List<Product> orderItems = order.getOrderItems();
        for (Product item : orderItems) {
            Optional<Product> optional = productDao.findById(item.getId());
            if (optional.isPresent()) {
                Product product = optional.get();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order.getId());
                orderDetail.setProductId(product.getId());
                orderDetail.setQty(item.getQuantity());
                int price = product.getPrice();
                orderDetail.setPrice(price);
                double tax = price * 0.15;
                orderDetail.setTaxPrice(tax);
                orderDetail.setTotalPrice(price + tax);
                orderDetailDao.save(orderDetail);
            }
        }
    }

    @Override
    public List<OrderMain> findOrderMainsByUserId(int userId) throws Exception {
        List<OrderMain> orderMainList = orderMainDao.findByUserId(userId);
        return orderMainList;
    }

    public OrderMain setupPay(OrderMain order) throws Exception {
        order.setIsPaid(true);
        order.setPaidAt(new Date());
        //        System.out.println(new Gson().toJson(order));
        order = orderMainDao.save(order);
        return order;
    }

    @Override
    public OrderMain setupCancelItems(UUID orderId) throws Exception {
        Optional<OrderMain> cancelOptional = orderMainDao.findById(orderId);
        OrderMain orderMain = cancelOptional.get();
        orderMain.setIsCancel(true);
        orderMain.setCancelAt(new Date());
        orderMainDao.save(orderMain);
        return orderMain;
    }

    public  OrderMain  setupCancelSingle(OrderMain order) throws Exception {
        int sum = 0;
        for (Product product : order.getOrderItems()) {
            List<OrderDetail> orderDetails = orderDetailDao.findByOrderIdAndProductId(order.getId(), product.getId());
            OrderDetail orderDetail = orderDetails.get(0);
            if (product.getQuantity() != orderDetail.getQty()) {
                orderDetail.setCancelQty(orderDetail.getQty() - product.getQuantity());
                orderDetail.setCancelAt(new Date());
                orderDetailDao.save(orderDetail);
                sum += (orderDetail.getPrice() * orderDetail.getQty());
            }
        }
        Optional<OrderMain> orderMainOption = orderMainDao.findById(order.getId());
        OrderMain orderMain = orderMainOption.get();
        orderMain.setItemsPrice(sum);
        double tax = sum * 0.15;
        orderMain.setTaxPrice(tax);
        orderMain.setTotalPrice(sum + tax);
        orderMainDao.save(orderMain);
        return orderMain;
    }
}
