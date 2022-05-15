package com.hunny.reijiproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunny.reijiproject.DTO.DishDto;
import com.hunny.reijiproject.entity.Dish;
import com.hunny.reijiproject.entity.DishFlavor;
import com.hunny.reijiproject.mapper.DishMapper;
import com.hunny.reijiproject.service.DishFlavorService;
import com.hunny.reijiproject.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author QiuZhengJie
 * @date 2022/5/10
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>implements DishService {
    @Autowired
       private DishFlavorService dishFlavorService;
    @Override
    public void saveWithFlavor(DishDto dishDto) {

                save(dishDto);
                Long dishId=dishDto.getId();
                List<DishFlavor> flavorList=dishDto.getFlavors();
                 //以下为第一种实现方式 *******************
//                List<DishFlavor>flavorList1=new ArrayList<>();
//                for(DishFlavor dishFlavor:flavorList)
//                {
//                    dishFlavor.setDishId(dishId);
//                    flavorList1.add(dishFlavor);
//                }
//                dishFlavorService.saveBatch(flavorList1);
                //以下为第二中实现方式***********************
              flavorList= flavorList.stream().map((iteam)->{
                          iteam.setDishId(dishId);
                          return iteam;
                 }).collect(Collectors.toList());
               dishFlavorService.saveBatch(flavorList);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish= getById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor>lambdaQueryWrapper=new LambdaQueryWrapper<>();
         lambdaQueryWrapper.eq(DishFlavor::getDishId,id);
                  List<DishFlavor> flavorList=dishFlavorService.list(lambdaQueryWrapper);
                  dishDto.setFlavors(flavorList);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
                updateById(dishDto);
         LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper=new LambdaQueryWrapper<>();
         lambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
             dishFlavorService.remove(lambdaQueryWrapper);
           List<DishFlavor>flavorList=  dishDto.getFlavors();
               flavorList=  flavorList.stream().map((e)->{
                     Long id=dishDto.getId();
                     e.setDishId(id);
                     return e;
                 }).collect(Collectors.toList());
               dishFlavorService.saveBatch(flavorList);
    }
}
