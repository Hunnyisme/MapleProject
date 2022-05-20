package com.hunny.reijiproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author QiuZhengJie
 * @date 2022/5/20
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public String f1()
    {
        return "/backend/index.html";
    }


}
