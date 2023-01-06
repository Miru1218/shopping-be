package com.shoppingbe.shoppingbe.facade;

import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserFacade {
    private UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    public User signup(User user, HttpServletRequest request) throws Exception {
        user = userService.signup(user, request);
        return user;
    }

    public User signIn(User user, HttpServletRequest request) throws Exception {
        user = userService.signIn(user, request);
        return user;
    }

    public User editProfile(User newUser, HttpServletRequest request) throws Exception {
        User user = userService.editProfile(newUser, request);
        return user;
    }

}
