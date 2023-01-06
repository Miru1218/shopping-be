package com.shoppingbe.shoppingbe.controller;

import com.shoppingbe.shoppingbe.entity.OrderDetail;
import com.shoppingbe.shoppingbe.entity.User;
import com.shoppingbe.shoppingbe.facade.UserFacade;
import com.shoppingbe.shoppingbe.repository.OrderDetailDao;
import com.shoppingbe.shoppingbe.repository.UserDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

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

    @Operation(summary = "TEST")
    @GetMapping(value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void test() {
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId(10);
        orderDetails.sort(Comparator.comparing(OrderDetail::getId));
        for (OrderDetail o : orderDetails) {
            System.out.println(o.getId());
        }
        orderDetails.sort(Comparator.comparing(OrderDetail::getId).reversed());
        for (OrderDetail o : orderDetails) {
            System.out.println(o.getId());
        }
    }

    @Operation(summary = "註冊")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<User> signup(@RequestBody User user, HttpServletRequest request) throws Exception {
        user = userFacade.signup(user, request);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    //    @Operation(summary = "登入")
    //    @PostMapping(value = "/signIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType
    //    .APPLICATION_JSON_VALUE)
    //    @ResponseBody
    ////    使用boolean來設定初始狀態
    //    public boolean signIn(@RequestBody String input, HttpServletRequest request) {
    //        System.out.println(input);
    ////        設定為沒有登入
    //        boolean result = false;
    ////        獲得前端輸入的資料
    //        JSONObject jsonObject = new JSONObject(input);
    ////        從前端獲得的資料拿出 account 和 password
    //        String account = jsonObject.getString("account");
    //        String password = jsonObject.getString("password");
    ////        從 DB 拿取 符合account 的 User 資料
    //        User user = userDao.findByAccount(account);
    ////        判斷 DB 有沒有這筆 account && 判斷輸入的密碼與資料庫是否正確
    //        if (user != null && password.equals(user.getPassword()))
    //            result = true;
    //        if (result) {
    //            HttpSession session = request.getSession();
    //            session.setAttribute("UserSession", user);
    //        }
    //        return result;
    //    }

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
