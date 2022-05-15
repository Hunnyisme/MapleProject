package com.hunny.reijiproject.controller;

import com.hunny.reijiproject.common.R;
import com.hunny.reijiproject.entity.Orders;
import com.hunny.reijiproject.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QiuZhengJie
 * @date 2022/5/14
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    /**
       用户下单
     * @param orders
     * @return com.hunny.reijiproject.common.R<java.lang.String>
     * @author QiuZhengJie

     */
@PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders)
    {
              orderService.submit(orders);
              return R.success("下单成功");
    }
}
