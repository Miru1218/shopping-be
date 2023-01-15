package com.shoppingbe.shoppingbe.service;

import com.google.gson.Gson;
import com.shoppingbe.shoppingbe.entity.*;
import com.shoppingbe.shoppingbe.repository.OrderDetailDao;
import com.shoppingbe.shoppingbe.repository.OrderMainDao;
import com.shoppingbe.shoppingbe.repository.ProductDao;
import com.shoppingbe.shoppingbe.repository.ShippingAddressDao;
import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    HttpSession httpSession;
    @Mock
    OrderMainDao orderMainDao;
    @Mock
    OrderDetailDao orderDetailDao;
    @Mock
    ShippingAddressDao shippingAddressDao;
    @Mock
    ProductDao productDao;
    @Captor
    ArgumentCaptor<OrderMain> orderCaptor;
    @Captor
    ArgumentCaptor<OrderDetail> orderDetailArgumentCaptor;
    @Captor
    ArgumentCaptor<ShippingAddress> shippingAddressArgumentCaptor;


    @Test
    void saveOrderMain() throws Exception {
        OrderMain orderMain = new OrderMain();
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        User user = new User();
        user.setId(1);
        when(httpSession.getAttribute(anyString())).thenReturn(user);
        when(orderMainDao.save(orderCaptor.capture())).thenReturn(new OrderMain());
        orderService.saveOrderMain(orderMain, httpServletRequest);
        Assertions.assertEquals(1, orderCaptor.getValue().getUserId());
        verify(orderMainDao, times(1)).save(any(OrderMain.class));
        verify(httpSession, times(1)).getAttribute(anyString());
        Assertions.assertTrue(new Date().getTime() > orderCaptor.getValue().getCreatedAt().getTime());
    }

    @Test
    void saveShippingAddress() throws Exception {
        OrderMain orderMain = new OrderMain();
        orderMain.setShippingAddress(new ShippingAddress());
        orderMain.setId(UUID.fromString("f16bd549-1eed-42bc-95e3-e986f4189f5f"));
        when(shippingAddressDao.save(shippingAddressArgumentCaptor.capture())).thenReturn(new ShippingAddress());
        orderService.saveShippingAddress(orderMain);
        Assertions.assertEquals("f16bd549-1eed-42bc-95e3-e986f4189f5f",
                shippingAddressArgumentCaptor.getValue().getOrderId().toString());
        verify(shippingAddressDao, times(1)).save(any(ShippingAddress.class));
    }

    @Test
    void setupOrderItems() throws Exception {
        OrderMain orderMain = new OrderMain();
        orderMain.setId(UUID.fromString("f16bd549-1eed-42bc-95e3-e986f4189f5f"));
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId(0);
        orderDetail.setPrice(100);
        orderDetail.setQty(10);
        orderDetails.add(orderDetail);
        Product product = new Product();
        product.setName("name");
        product.setSlug("slug");
        product.setImage("image");
        Optional<Product> productOptional = Optional.of(product);
        when(productDao.findById(anyInt())).thenReturn(productOptional);
        when(orderDetailDao.findByOrderId(any(UUID.class))).thenReturn(orderDetails);

        OrderMain order = orderService.setupOrderItems(orderMain);

        Assertions.assertEquals("name", product.getName());
        Assertions.assertEquals("slug", product.getSlug());
        Assertions.assertEquals("image", product.getImage());
        Assertions.assertEquals(100, orderDetail.getPrice());
        Assertions.assertEquals(10, orderDetail.getQty());

        verify(orderDetailDao, times(1)).findByOrderId(any(UUID.class));
        verify(productDao, times(1)).findById(anyInt());
    }

    @Test
    void saveOrderDetails() throws Exception {
        OrderMain orderMain = new OrderMain();
        List<Product> orderItems = new ArrayList<>();
        Product productFromFrontend = new Product();
        orderItems.add(productFromFrontend);
        productFromFrontend.setId(1);
        productFromFrontend.setName("旗袍");
        productFromFrontend.setSlug("旗袍");
        productFromFrontend.setCategory("旗袍");
        productFromFrontend.setImage("/images/p1.jpg");
        productFromFrontend.setPrice(120);
        productFromFrontend.setCountInStock(10);
        productFromFrontend.setBrand("adidas");
        productFromFrontend.setRating(4.5);
        productFromFrontend.setNumReviews(2);
        productFromFrontend.setDescription("旗袍");
        productFromFrontend.setQuantity(1);
        orderMain.setOrderItems(orderItems);
        orderMain.setId(UUID.fromString("f16bd549-1eed-42bc-95e3-e986f4189f5f"));
        Product product = new Product();
        product.setId(1);
        product.setPrice(100);
        Optional<Product> optional = Optional.of(product);
        when(productDao.findById(anyInt())).thenReturn(optional);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setTaxPrice(115);
        orderDetail.setTotalPrice(215);
        when(orderDetailDao.save(orderDetailArgumentCaptor.capture())).thenReturn(orderDetail);
        orderService.saveOrderDetails(orderMain);
        Assertions.assertEquals("f16bd549-1eed-42bc-95e3-e986f4189f5f",
                orderDetailArgumentCaptor.getValue().getOrderId().toString());
        Assertions.assertEquals(1, orderDetailArgumentCaptor.getValue().getProductId());
        Assertions.assertEquals(1, orderDetailArgumentCaptor.getValue().getQty());
        Assertions.assertEquals(100, orderDetailArgumentCaptor.getValue().getPrice());
        Assertions.assertEquals(15, orderDetailArgumentCaptor.getValue().getTaxPrice());
        Assertions.assertEquals(115, orderDetailArgumentCaptor.getValue().getTotalPrice());
        verify(productDao, times(1)).findById(anyInt());
        verify(orderDetailDao, times(1)).save(any(OrderDetail.class));

    }

    @Test
    void setupPay() throws Exception {
        OrderMain orderMain = new OrderMain();
        when(orderMainDao.save(orderCaptor.capture())).thenReturn(orderMain);
        orderService.setupPay(orderMain);
        Assertions.assertTrue(orderCaptor.getValue().getIsPaid());
        Assertions.assertTrue(new Date().getTime() > orderCaptor.getValue().getPaidAt().getTime());
        verify(orderMainDao, times(1)).save(any(OrderMain.class));
    }

    @Test
    void setupCancelItems() throws Exception {
        OrderMain orderMain = new OrderMain();
        Optional<OrderMain> cancelOptional = Optional.of(orderMain);
        when(orderMainDao.findById(any(UUID.class))).thenReturn(cancelOptional);
        when(orderMainDao.save(orderCaptor.capture())).thenReturn(orderMain);
        orderService.setupCancelItems(UUID.fromString("1c269b97-7f68-4a9e-85f4-8b778c1c6800"));

        Assertions.assertTrue(orderCaptor.getValue().getIsCancel());
        Assertions.assertTrue(new Date().getTime() > orderCaptor.getValue().getCancelAt().getTime());
        verify(orderMainDao, times(1)).save(any(OrderMain.class));
        verify(orderMainDao, times(1)).findById(any(UUID.class));
    }

    @Test
    void setupCancelSingle() throws Exception {
        int sum = 0;
        OrderMain orderMain = new OrderMain();
        orderMain.setId(UUID.fromString("138d37ae-b769-4d5d-b592-e7c152fd5a9e"));
        List<Product> orderItems = new ArrayList<>();
        Product product = new Product();
        product.setId(3);
        product.setQuantity(1);
        orderItems.add(product);
        orderMain.setOrderItems(orderItems);
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQty(10);
        orderDetail.setCancelQty(orderDetail.getQty() - product.getQuantity());
        orderDetail.setPrice(130);
        orderDetails.add(orderDetail);
        when(orderDetailDao.findByOrderIdAndProductId(any(UUID.class), anyInt())).thenReturn(orderDetails);
        when(orderDetailDao.save(orderDetailArgumentCaptor.capture())).thenReturn(orderDetail);
        Optional<OrderMain> orderMainOption = Optional.of(orderMain);
        when(orderMainDao.findById(any(UUID.class))).thenReturn(orderMainOption);
        when(orderMainDao.save(orderCaptor.capture())).thenReturn(orderMain);

        orderService.setupCancelSingle(orderMain);
    }
}