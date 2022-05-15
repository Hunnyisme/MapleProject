package com.hunny.reijiproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunny.reijiproject.entity.OrderDetail;
import com.hunny.reijiproject.mapper.OrderDetailMapper;
import com.hunny.reijiproject.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author QiuZhengJie
 * @date 2022/5/14
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>implements OrderDetailService {
}
