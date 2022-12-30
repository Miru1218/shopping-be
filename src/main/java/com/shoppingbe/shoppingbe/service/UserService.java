package com.shoppingbe.shoppingbe.service;

import com.shoppingbe.shoppingbe.entity.User;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User signup(String input, HttpServletRequest request) throws Exception;

    User signIn(String input, HttpServletRequest request) throws Exception;

    User editProfile(JSONObject input, HttpServletRequest request) throws Exception;
}
