package com.hunny.reijiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunny.reijiproject.common.BaseContext;
import com.hunny.reijiproject.common.R;
import com.hunny.reijiproject.entity.ShoppingCart;
import com.hunny.reijiproject.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author QiuZhengJie
 * @date 2022/5/14
 */
@RestController
@RequestMapping("/shoppingCart")
public class shoppingCartController {
    @Autowired
    ShoppingCartService service;
/**
   查看购物车
 * @return com.hunny.reijiproject.common.R<java.lang.String>
 * @author QiuZhengJie

 */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list()
    {
Long currentUserId=BaseContext.getCurrentId();
LambdaQueryWrapper<ShoppingCart>lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,currentUserId);
        lambdaQueryWrapper.orderByDesc(ShoppingCart::getCreateTime);

     return  R.success(service.list(lambdaQueryWrapper));

    }
    /**
       添加购物车
     * @param shoppingCart
     * @return com.hunny.reijiproject.common.R<com.hunny.reijiproject.entity.ShoppingCart>
     * @author QiuZhengJie

     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart)
    {
        System.out.println("收到的购物车参数："+shoppingCart);
        Long currentId= BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        Long dishId=shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId!=null)
        {
            lambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
              lambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

              ShoppingCart shoppingCart1=service.getOne(lambdaQueryWrapper);
               if(shoppingCart1!=null)
               {
                   Integer num=shoppingCart1.getNumber();
                   shoppingCart1.setNumber(num+1);
                   service.updateById(shoppingCart1);

               }else{
                   shoppingCart.setNumber(1);
                   shoppingCart.setCreateTime(LocalDateTime.now());
                   service.save(shoppingCart);
                   shoppingCart1=shoppingCart;
               }


        return R.success(shoppingCart1);
    }
    /**
       清空购物车
     * @return com.hunny.reijiproject.common.R<java.lang.String>
     * @author QiuZhengJie

     */
        @DeleteMapping("/clean")
       public R<String> clean()
       {
          Long userId=BaseContext.getCurrentId();
          LambdaQueryWrapper<ShoppingCart>lambdaQueryWrapper=new LambdaQueryWrapper<>();
          lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
          service.remove(lambdaQueryWrapper);
          return R.success("清空成功");
       }

}
