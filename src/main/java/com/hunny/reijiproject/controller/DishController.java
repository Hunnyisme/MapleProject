package com.hunny.reijiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.hunny.reijiproject.DTO.DishDto;
import com.hunny.reijiproject.common.R;
import com.hunny.reijiproject.entity.Category;
import com.hunny.reijiproject.entity.Dish;
import com.hunny.reijiproject.entity.DishFlavor;
import com.hunny.reijiproject.service.CategoryService;
import com.hunny.reijiproject.service.DishFlavorService;
import com.hunny.reijiproject.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 菜品管理
 * @author QiuZhengJie
 * @date 2022/5/11
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    RedisTemplate redisTemplate;
@PostMapping
    public R<String> save(@RequestBody DishDto dishDto)
    {
        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功");
    }
    /**
       菜品信息分页查询
     * @param page 当前页
     * @param pageSize 一页有多少记录
     * @param name  模糊搜索名
     * @return com.hunny.reijiproject.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @author QiuZhengJie

     */
    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize,String name)
    {
             Page<Dish> pageInfo=new Page<>(page,pageSize);
             Page<DishDto>dishDtoPage=new PageDTO<>();
        LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo);
        //对象的拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish>records=pageInfo.getRecords();

        List<DishDto>dtoRecords=null;
       dtoRecords= records.stream().map((e)->{
           DishDto dishDto=new DishDto();
             Long catId= e.getCategoryId();
              Category category= categoryService.getById(catId);
              if(category!=null)
              {
                  String categoryName=  category.getName();

                  dishDto.setCategoryName(categoryName);
              }
           BeanUtils.copyProperties(e,dishDto);
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dtoRecords);

        return R.success(dishDtoPage);
    }
    @GetMapping("/{id}")
    public R<DishDto> getUpdateMessage(@PathVariable Long id)
    {
          DishDto dishDto=dishService.getByIdWithFlavor(id);
          return R.success(dishDto);
    }
    /**
       修改菜品
     * @param dishDto
     * @return com.hunny.reijiproject.common.R<java.lang.String>
     * @author QiuZhengJie

     */

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto)
    {
        dishService.updateWithFlavor(dishDto);

        return R.success("修改菜品成功");
    }
    /**
       根据分类id查询菜品数据
     * @param dish
     * @return com.hunny.reijiproject.common.R<java.util.List<com.hunny.reijiproject.entity.Dish>>
     * @author QiuZhengJie

     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish)
//    {
//        LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//       List<Dish>list= dishService.list(lambdaQueryWrapper);
//        return R.success(list);
//    }
    /**
       先从缓存中查询数据，如果不存在则查询数据库，然后将数据存到缓存中
     * @param dish
     * @return com.hunny.reijiproject.common.R<java.util.List<com.hunny.reijiproject.DTO.DishDto>>
     * @author QiuZhengJie

     */

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish)
    {
        String key="dish_"+dish.getCategoryId();
        List<DishDto> dishDtoList=null;


        Optional o=Optional.ofNullable(redisTemplate.opsForValue().get(key));


        if(o.isPresent()
        )
        {
            dishDtoList= (List<DishDto>) o.get();
            return R.success(dishDtoList);
        }
        LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish>list= dishService.list(lambdaQueryWrapper);
        dishDtoList=list.stream().map((e)->{
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(e,dishDto);
            Long cateId=e.getCategoryId();
            Category category =categoryService.getById(cateId);
            if(category!=null)
            {
                String cateName= category.getName();
                dishDto.setCategoryName(cateName);
            }
            Long dishId=e.getId();
            LambdaQueryWrapper<DishFlavor>lambdaQueryWrapper1=new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(DishFlavor::getDishId,dishId);
         List<DishFlavor>flavorList=   dishFlavorService.list(lambdaQueryWrapper1);
         dishDto.setFlavors(flavorList);
         return dishDto;
        }).collect(Collectors.toList());
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
        return R.success(dishDtoList);
    }

}
