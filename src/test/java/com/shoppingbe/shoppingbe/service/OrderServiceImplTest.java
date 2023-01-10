package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    HttpSession httpSession;
    @Captor
    ArgumentCaptor<User> userCaptor;

    //    @Test
    //    void getOrderMainByOrderId() {
    //    }

    @Test
    void saveOrderMain() {
    }

    @Test
    void setupShippingAddress() {
    }

    @Test
    void saveShippingAddress() {

    }

    @Test
    void setupOrderItems() {
    }

    @Test
    void saveOrderDetails() {
    }

    @Test
    void findOrderMainsByUserId() {
    }
}