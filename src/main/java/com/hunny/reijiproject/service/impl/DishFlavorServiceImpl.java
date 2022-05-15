package com.hunny.reijiproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunny.reijiproject.entity.DishFlavor;
import com.hunny.reijiproject.mapper.DishFlavorMapper;
import com.hunny.reijiproject.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author QiuZhengJie
 * @date 2022/5/11
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>implements DishFlavorService {
}
