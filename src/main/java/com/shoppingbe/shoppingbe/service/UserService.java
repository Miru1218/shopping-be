package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User signup(User user, HttpServletRequest request) throws Exception;

    User signIn(User user, HttpServletRequest request) throws Exception;

    User editProfile(User newUser, HttpServletRequest request) throws Exception;
}
