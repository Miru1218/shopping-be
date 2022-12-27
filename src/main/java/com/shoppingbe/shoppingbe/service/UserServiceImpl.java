package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.repository.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User signup(String input, HttpServletRequest request) throws Exception {
        System.out.println(input);
        JSONObject jsonObject = new JSONObject(input);
        User user = new User();
        user.setAccount(jsonObject.getString("account"));
        user.setPassword(jsonObject.getString("password"));
        user.setEmail(jsonObject.getString("email"));
        user.setAddress(jsonObject.getString("address"));
        user.setPhone(jsonObject.getInt("phone"));
        userDao.save(user);
        HttpSession session = request.getSession();
        session.setAttribute("UserSession", user);
        return user;
    }

    @Override
    public User signIn(String input, HttpServletRequest request) throws Exception {
        boolean loginPass = false;
        JSONObject inputObject = new JSONObject(input);
        User user = userDao.findByAccount(inputObject.getString("account"));
        if (user != null && user.getPassword().equals(inputObject.getString("password"))) {
            loginPass = true;
            HttpSession session = request.getSession();
            session.setAttribute("UserSession", user);
            user.setPassword(null);
        }
        return user;
    }
}
