package com.hunny.reijiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunny.reijiproject.common.R;
import com.hunny.reijiproject.entity.Category;
import com.hunny.reijiproject.entity.Setmeal;
import com.hunny.reijiproject.DTO.SetmealDto;
import com.hunny.reijiproject.service.CategoryService;
import com.hunny.reijiproject.service.SetMealDishService;
import com.hunny.reijiproject.service.SetmealService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 * @author QiuZhengJie
 * @date 2022/5/12
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetMealDishService setMealDishService;
    @Autowired
    private CategoryService categoryService;
    /**
       新增套餐
     * @param setmealDto
     * @return com.hunny.reijiproject.common.R<java.lang.String>
     * @author QiuZhengJie

     */
      @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto)
    {
        System.out.println("***********封装数据**************");
        System.out.println(setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }
    @GetMapping("/page")
        public R<Page> page(Integer page,Integer pageSize,String name)
        {
            Page<Setmeal> pageInfo=new Page<>(page,pageSize);
            Page<SetmealDto> setmealDtoPage=new Page<>();

            LambdaQueryWrapper<Setmeal>lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Setmeal::getName,name);
            lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
            setmealService.page(pageInfo,lambdaQueryWrapper);
            BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
                List<Setmeal>setmealList= pageInfo.getRecords();
             List<SetmealDto>setmealDtoList=   setmealList.stream().map((e)->{
                    SetmealDto setmealDto=new SetmealDto();
                      Long cateId= e.getCategoryId();
                   Category category= categoryService.getById(cateId);
                  String catname=  category.getName();
                  setmealDto.setCategoryName(catname);
                  BeanUtils.copyProperties(e,setmealDto);
                    return setmealDto;
                }).collect(Collectors.toList());
           setmealDtoPage.setRecords(setmealDtoList);
           return R.success(setmealDtoPage);
        }
        /**
           删除套餐
         * @param ids
         * @return com.hunny.reijiproject.common.R<java.lang.String>
         * @author QiuZhengJie

         */
        @DeleteMapping
        public R<String> delete(@RequestParam List<Long>ids)
        {
            System.out.println("*********接收到参数**********");
            System.out.println(ids);
            setmealService.removeWithDish(ids);
            return R.success("删除套餐成功");

        }
        /**
           根据条件查询套餐数据
         * @param setmeal
         * @return com.hunny.reijiproject.common.R<java.util.List<com.hunny.reijiproject.entity.Setmeal>>
         * @author QiuZhengJie

         */
        @GetMapping("/list")
        public R<List<Setmeal>> list( Setmeal setmeal)
        {
          LambdaQueryWrapper<Setmeal>lambdaQueryWrapper=new LambdaQueryWrapper<>();
          lambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
          lambdaQueryWrapper.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());

          return R.success(setmealService.list(lambdaQueryWrapper));
        }
}
