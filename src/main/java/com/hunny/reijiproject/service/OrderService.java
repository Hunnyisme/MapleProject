package com.hunny.reijiproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunny.reijiproject.entity.Orders;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author QiuZhengJie
 * @date 2022/5/14
 */
public interface OrderService extends IService<Orders> {
        @Transactional
    public void submit(Orders orders);
}
