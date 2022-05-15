package com.hunny.reijiproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunny.reijiproject.common.CustomException;
import com.hunny.reijiproject.entity.Category;
import com.hunny.reijiproject.entity.Dish;
import com.hunny.reijiproject.entity.Setmeal;
import com.hunny.reijiproject.mapper.CategoryMapper;
import com.hunny.reijiproject.service.CategoryService;
import com.hunny.reijiproject.service.DishService;
import com.hunny.reijiproject.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @author QiuZhengJie
 * @date 2022/5/10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
          @Autowired
    private DishService dishService;
      @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<Dish>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        long count=dishService.count(lambdaQueryWrapper);
        if(count>0)
        {
          throw new CustomException("该分类包含了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal>lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId,ids);
        long count2=setmealService.count(lambdaQueryWrapper1);
        if(count2>0)
        {
           throw  new CustomException("该分类包含了套餐，不能删除");
        }

        removeById(ids);
    }
}
