package com.shoppingbe.shoppingbe.controller;

import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.facade.UserFacade;
import com.shoppingbe.shoppingbe.repository.OrderDetailDao;
import com.shoppingbe.shoppingbe.repository.UserDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController")
public class UserController {
    @Resource(name = "userDao")
    UserDao userDao;
    @Resource(name = "orderDetailDao")
    OrderDetailDao orderDetailDao;
    @Resource
    UserFacade userFacade;

    @Operation(summary = "註冊")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> signup(@RequestBody User user, HttpServletRequest request) throws Exception {
        user = userFacade.signup(user, request);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @Operation(summary = "登入")
    @PostMapping(value = "/signIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> signIn(@RequestBody User user, HttpServletRequest request) throws Exception {
        user = userFacade.signIn(user, request);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "修改個人檔案")
    @PutMapping(value = "/profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> editProfile(@RequestBody User user, HttpServletRequest request) throws Exception {
        user = userFacade.editProfile(user, request);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}