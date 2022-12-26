package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.OrderDetail;
import com.shoppingbe.shoppingbe.entity.OrderMain;
import com.shoppingbe.shoppingbe.entity.Product;
import com.shoppingbe.shoppingbe.entity.ShippingAddress;
import com.shoppingbe.shoppingbe.model.Order;
import com.shoppingbe.shoppingbe.model.OrderItem;
import com.shoppingbe.shoppingbe.repository.OrderDetailDao;
import com.shoppingbe.shoppingbe.repository.OrderMainDao;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import com.shoppingbe.shoppingbe.repository.ShippingAddressDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {
    private OrderMainDao orderMainDao;
    private ShippingAddressDao shippingAddressDao;
    private OrderDetailDao orderDetailDao;

    private ProductDao productDao;

    public OrderServiceImpl(OrderMainDao orderMainDao, ShippingAddressDao shippingAddressDao,
                            OrderDetailDao orderDetailDao,ProductDao productDao) {
        this.orderMainDao = orderMainDao;
        this.shippingAddressDao = shippingAddressDao;
        this.orderDetailDao = orderDetailDao;
        this.productDao = productDao;
    }
    @Override
    public Order getOrderMainByOrderId(int orderId) throws Exception {
        Order order = new Order();
        Optional<OrderMain> orderMainOptional = orderMainDao.findById(orderId);
        if (orderMainOptional.isPresent()) {
            OrderMain orderMain = orderMainOptional.get();
            order.setId(orderId);
            order.setItemsPrice(orderMain.getItemsPrice());
            order.setShippingPrice(orderMain.getShippingPrice());
            order.setTaxPrice(orderMain.getTaxPrice());
            order.setTotalPrice(orderMain.getTotalPrice());
            order.setPaymentMethod(orderMain.getPaymentMethod());
            order.setPaid(true);
        }
        return order;
    }
    @Override
    public Order setupShippingAddress(Order order) throws Exception {
        if (order.getId() != null) {
            List<ShippingAddress> shippingAddressList = shippingAddressDao.findByOrderId(order.getId());
            ShippingAddress shippingAddress = shippingAddressList.get(0);
            order.setDelivered(true);
            order.setShippingAddress(shippingAddress);
        }
        return order;
    }

    @Override
    public Order setupOrderItems(Order order) throws Exception {
        if (order.getId() != null) {
            List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(order.getId());
            orderDetails.sort(Comparator.comparing(OrderDetail::getId).reversed());
            List<OrderItem> orderItems = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetails) {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(orderDetail.getProductId());
                Optional<Product> productOptional = productDao.findById(orderDetail.getProductId());
                Product product = productOptional.get();
                orderItem.setName(product.getName());
                orderItem.setSlug(product.getSlug());
                orderItem.setImage(product.getImage());
                orderItem.setPrice(orderDetail.getItemsPrice());
                orderItem.setQuantity(orderDetail.getQty());
                orderItems.add(orderItem);
            }
            order.setOrderItems(orderItems);
        }
        return order;
    }
}
