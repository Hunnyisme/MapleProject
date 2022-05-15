package com.hunny.reijiproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunny.reijiproject.common.BaseContext;
import com.hunny.reijiproject.common.CustomException;
import com.hunny.reijiproject.entity.*;
import com.hunny.reijiproject.mapper.OrderMapper;
import com.hunny.reijiproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author QiuZhengJie
 * @date 2022/5/14
 */
@Service
public class OrderServiceImp extends ServiceImpl<OrderMapper, Orders>implements OrderService {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    UserService userService;
    @Autowired
    AddressBookService addressBookService;
    @Autowired
    OrderDetailService orderDetailService;

    @Override
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId= BaseContext.getCurrentId();

        //查询当前用户购物车数据
        LambdaQueryWrapper<ShoppingCart>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
       List<ShoppingCart> shoppingCartList=shoppingCartService.list(lambdaQueryWrapper);
       if(shoppingCartList==null||shoppingCartList.size()==0)
       {
           throw new CustomException("购物车为空，无法下单");
       }
        //查询用户数据
        User user=userService.getById(userId);

       //查询地址数据


    AddressBook addressBook= addressBookService.getById(orders.getAddressBookId()) ;
                if(addressBook==null)
                {
                    throw new CustomException("地址信息为空，不能下单");
                }
        //往订单表插入数据(一条数据)
         Long orderId= IdWorker.getId();//订单号
        AtomicInteger amount=new AtomicInteger(0);
             List<OrderDetail>orderDetailList=shoppingCartList.stream().map((e)->{
                 OrderDetail orderDetail = new OrderDetail();
                 orderDetail. setOrderId (orderId);
                 orderDetail. setNumber (e. getNumber ());
                         orderDetail. setDishFlavor (e. getDishFlavor ());
                                 orderDetail. setDishId (e. getDishId());
                 orderDetail. setSetmealId (e. getSetmealId());
                         orderDetail. setName (e. getName ());
                                 orderDetail. setImage (e. getImage ());
                                         orderDetail. setAmount (e. getAmount ());
                                         amount.getAndAdd(e.getAmount().multiply(new BigDecimal(e.getNumber())).intValue());
                                         return orderDetail;
                    }).collect(Collectors.toList());

          orders.setNumber(String.valueOf(orderId));//设置订单号
               orders. setId (orderId);
              orders. setOrderTime (LocalDateTime.now() );
                orders. setCheckoutTime (LocalDateTime. now());
                        orders. setStatus (2);
                        orders. setAmount (new BigDecimal(amount. get()));
                    orders. setUserId (userId);

                  orders. setUserName (user. getName ());
                orders. setConsignee (addressBook. getConsignee ());
                        orders. setPhone (addressBook. getPhone ());
                        orders. setAddress ( (addressBook. getProvinceName()== null?"": addressBook. getProvinceName ())
                                + (addressBook. getCityName()== null?"": addressBook. getCityName() )
                        + (addressBook. getDistrictName () == null?"": addressBook. getDistrictName ())
                        + (addressBook. getDetail()== null ?"" : addressBook. getDetail()));
                        save(orders);
        //往订单明细表插入数据(可能多条数据）
           orderDetailService.saveBatch(orderDetailList);

           //清空购物车
        shoppingCartService.remove(lambdaQueryWrapper);
    }
}
