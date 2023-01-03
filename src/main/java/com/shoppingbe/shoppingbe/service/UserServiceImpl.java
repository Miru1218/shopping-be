package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.repository.UserDao;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User signup(String input, HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject(input);
        User user = new User();
        user.setAccount(jsonObject.getString("account"));
        user.setPassword(jsonObject.getString("password"));
        user.setEmail(jsonObject.getString("email"));
        user.setAddress(jsonObject.getString("address"));
        user.setPhone(jsonObject.getString("phone"));
        userDao.save(user);
        HttpSession session = request.getSession();
        session.setAttribute("UserSession", user);
        return user;
    }

    @Override
    public User signIn(User user, HttpServletRequest request) throws Exception {
        boolean loginPass = false;
        User userFromDb = userDao.findByAccount(user.getAccount());
        if (userFromDb != null && userFromDb.getPassword().equals(user.getPassword())) {
            loginPass = true;
            HttpSession session = request.getSession();
            session.setAttribute("UserSession", userFromDb);
        }
        userFromDb.setLoginPass(loginPass);
        return userFromDb;
    }
    @Override
    public User editProfile(JSONObject input, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("UserSession");
        user = userDao.findByAccount(user.getAccount());
        if(Strings.isNotBlank(input.getString("account")))
            user.setAccount(input.getString("account"));
        if(Strings.isNotBlank(input.getString("email")))
            user.setEmail(input.getString("email"));
        if(Strings.isNotBlank(input.getString("phone")))
            user.setPhone(input.getString("phone"));
        if(Strings.isNotBlank(input.getString("password")))
            user.setPassword(input.getString("password"));
        userDao.save(user);
        session.setAttribute("UserSession",user);
        return user;
    }
}
