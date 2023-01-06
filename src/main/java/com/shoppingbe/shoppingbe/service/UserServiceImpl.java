package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.repository.UserDao;
import org.apache.logging.log4j.util.Strings;
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
    public User signup(User user, HttpServletRequest request) throws Exception {
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
        } else {
            userFromDb = new User();
        }
        userFromDb.setLoginPass(loginPass);
        return userFromDb;
    }

    @Override
    public User editProfile(User newUser, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("UserSession");
//        user = userDao.findByAccount(user.getAccount());
        if (Strings.isNotBlank(newUser.getAccount()))
            user.setAccount(newUser.getAccount());
        if (Strings.isNotBlank(newUser.getEmail()))
            user.setEmail(newUser.getEmail());
        if (Strings.isNotBlank(newUser.getPhone()))
            user.setPhone(newUser.getPhone());
        if (Strings.isNotBlank(newUser.getPassword()))
            user.setPassword(newUser.getPassword());
        userDao.save(user);
        session.setAttribute("UserSession", user);
        return user;
    }
}
