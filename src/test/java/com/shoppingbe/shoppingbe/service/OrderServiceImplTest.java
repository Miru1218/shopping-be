package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.OrderDetail;
import com.shoppingbe.shoppingbe.entity.OrderMain;
import com.shoppingbe.shoppingbe.entity.ShippingAddress;
import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.repository.OrderDetailDao;
import com.shoppingbe.shoppingbe.repository.OrderMainDao;
import com.shoppingbe.shoppingbe.repository.ShippingAddressDao;
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
import java.util.Date;
import java.util.UUID;

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
    @Captor
    ArgumentCaptor<OrderMain> orderCaptor;
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
    void setupOrderItems() {


    }

    @Test
    void saveOrderDetails() {
    }

    @Test
    void setupPay() throws Exception {
        OrderMain orderMain = new OrderMain();
        when(orderMainDao.save(orderCaptor.capture())).thenReturn(orderMain);
        orderService.setupPay(orderMain);
        Assertions.assertTrue(orderCaptor.getValue().isPaid());
        verify(orderMainDao, times(1)).save(any(OrderMain.class));
        Assertions.assertTrue(new Date().getTime() > orderCaptor.getValue().getCreatedAt().getTime());
    }
}