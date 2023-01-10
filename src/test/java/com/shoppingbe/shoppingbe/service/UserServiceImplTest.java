package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.repository.UserDao;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    HttpServletRequest httpServletRequest;
    @Mock
    UserDao userDao;
    @Mock
    HttpSession httpSession;
    @Captor
    ArgumentCaptor<User> userCaptor;

    @Test
    void signup_test_with_correct_field_value() throws Exception {
        User user = new User();
        user.setAccount("lulu2");
        user.setAddress("2");
        user.setEmail("2@2.com");
        user.setPassword("2");
        user.setPhone("222");
        when(userDao.save(userCaptor.capture())).thenReturn(new User());
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        doNothing().when(httpSession).setAttribute(anyString(), any());
        userService.signup(user, httpServletRequest);
        Assertions.assertEquals("lulu2", userCaptor.getValue().getAccount());
        Assertions.assertEquals("2", userCaptor.getValue().getAddress());
        Assertions.assertEquals("2@2.com", userCaptor.getValue().getEmail());
        Assertions.assertEquals("2", userCaptor.getValue().getPassword());
        Assertions.assertEquals("222", userCaptor.getValue().getPhone());
        verify(userDao, times(1)).save(any(User.class));
        verify(httpSession, times(1)).setAttribute(anyString(), any());
    }

    @Test
    void signIn_test_with_wrong_username_and_correct_password() throws Exception {
        User user = new User();
        user.setAccount("lulu3");
        user.setPassword("4");
        user.setLoginPass(false);

        when(userDao.findByAccount(eq("lulu3"))).thenReturn(null);
        User result = userService.signIn(user, httpServletRequest);
        verify(userDao, times(1)).findByAccount(eq("lulu3"));
        verify(httpSession, times(0)).setAttribute(anyString(), any());
        Assertions.assertFalse(result.isLoginPass());
    }

    @Test
    void signIn_test_with_correct_username_and_wrong_password() throws Exception {
        User user = new User();
        user.setAccount("lulu3");
        user.setPassword("4");
        user.setLoginPass(false);

        User userFromDB = new User();
        userFromDB.setAccount("lulu3");
        userFromDB.setPassword("5");


        when(userDao.findByAccount(eq("lulu3"))).thenReturn(userFromDB);
        User result = userService.signIn(user, httpServletRequest);
        verify(userDao, times(1)).findByAccount(eq("lulu3"));
        verify(httpSession, times(0)).setAttribute(anyString(), any());
        Assertions.assertFalse(result.isLoginPass());

    }

    @Test
    void signIn_test_with_correct_username_and_password() throws Exception {
        User user = new User();
        user.setAccount("lulu3");
        user.setPassword("4");
        user.setLoginPass(false);

        when(userDao.findByAccount(eq("lulu3"))).thenReturn(user);
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        doNothing().when(httpSession).setAttribute(anyString(), any());
        User result = userService.signIn(user, httpServletRequest);
        verify(userDao, times(1)).findByAccount(eq("lulu3"));
        verify(httpSession, times(1)).setAttribute(anyString(), any());
        Assertions.assertTrue(result.isLoginPass());
    }


    @Test
    void editProfile_test_update_success() throws Exception {
        User newUser = new User();
        newUser.setAccount("lulu2");
        newUser.setEmail("2@2.com");
        newUser.setPhone("222");
        newUser.setPassword("2");
        User userFromDB = new User();
        userFromDB.setAccount("lulu3");
        userFromDB.setPassword("3");
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(anyString())).thenReturn(userFromDB);
        when(userDao.save(userCaptor.capture())).thenReturn(newUser);
        doNothing().when(httpSession).setAttribute(anyString(), any(User.class));
        userService.editProfile(newUser, httpServletRequest);

        Assertions.assertEquals("lulu2", userCaptor.getValue().getAccount());
        Assertions.assertEquals("2@2.com", userCaptor.getValue().getEmail());
        Assertions.assertEquals("2", userCaptor.getValue().getPassword());
        Assertions.assertEquals("222", userCaptor.getValue().getPhone());

    }

    @Test
    void editProfile_test_update_password_success() throws Exception {
        User newUser = new User();
        newUser.setAccount("lulu3");
        newUser.setPassword("2");
        User userFromDB = new User();
        userFromDB.setAccount("lulu3");
        userFromDB.setPassword("3");
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute(anyString())).thenReturn(userFromDB);
        when(userDao.save(userCaptor.capture())).thenReturn(newUser);
        doNothing().when(httpSession).setAttribute(anyString(), any(User.class));
        userService.editProfile(newUser, httpServletRequest);
        Assertions.assertEquals("lulu3", userCaptor.getValue().getAccount());
        Assertions.assertEquals("2", userCaptor.getValue().getPassword());
    }

}