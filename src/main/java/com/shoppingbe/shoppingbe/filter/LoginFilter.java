package com.shoppingbe.shoppingbe.filter;

import com.google.gson.Gson;
import com.shoppingbe.shoppingbe.entity.User;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@WebFilter(urlPatterns = {"/*"})
@Order(value = 1)
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("UserSession");
        String uri = new String(req.getRequestURI());
        System.out.println("uri:" + uri);

        //放行URI
        List<String> allowUris = Arrays.asList("/user/signup", "/product/products");

        // 放行所有靜態檔案
        if (uri.contains("/css") || uri.contains("/images") || uri.contains("/js")) {
            chain.doFilter(request, response);
            return;
        }

        // 已登入
        if (user != null) {
            System.out.println(new Gson().toJson(user));
            chain.doFilter(request, response);
        }
        // 未登入
        else {
//            if (allowUris.contains(uri)) {
                chain.doFilter(request, response);
//            } else {
//                res.setStatus(HttpStatus.UNAUTHORIZED.value());
//            }
        }
    }
}
