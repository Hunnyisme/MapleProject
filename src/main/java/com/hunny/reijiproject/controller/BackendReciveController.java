package com.hunny.reijiproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于接待跳转到登陆页面
 * @author QiuZhengJie
 * @date 2022/5/15
 */
@RestController
public class BackendReciveController {

    @RequestMapping("/welcome")
    public void showLogin(HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        response.sendRedirect("/backend/index.html");
    }
}
