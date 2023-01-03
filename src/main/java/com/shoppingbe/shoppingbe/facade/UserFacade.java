package com.shoppingbe.shoppingbe.facade;

import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.service.UserService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserFacade {
    private UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    public User signup(String input, HttpServletRequest request) throws Exception {
        User user = userService.signup(input, request);
        return user;
    }

    public User signIn(User user, HttpServletRequest request) throws Exception {
        user = userService.signIn(user, request);
        return user;
    }

    public User editProfile(JSONObject input, HttpServletRequest request) throws Exception {
        User user = userService.editProfile(input, request);
        return user;
    }

}
