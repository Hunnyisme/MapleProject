package com.hunny.reijiproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author QiuZhengJie
 * @date 2022/5/19
 */
@RestController
public class FrontReciveController {

    @RequestMapping("/welcome2")
    public void showLogin(HttpServletRequest request, HttpServletResponse response)throws Exception
    {
        response.sendRedirect("front/index.html");
    }
}
