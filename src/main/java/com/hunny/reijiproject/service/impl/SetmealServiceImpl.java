package com.hunny.reijiproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunny.reijiproject.common.CustomException;
import com.hunny.reijiproject.entity.Setmeal;
import com.hunny.reijiproject.entity.SetmealDish;
import com.hunny.reijiproject.DTO.SetmealDto;
import com.hunny.reijiproject.mapper.SetmealMapper;
import com.hunny.reijiproject.service.SetMealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author QiuZhengJie
 * @date 2022/5/10
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>implements com.hunny.reijiproject.service.SetmealService {
@Autowired
    private SetMealDishService setMealDishService;

    @Override
    public void saveWithDish(SetmealDto setmealDto) {
          save(setmealDto);
        List<SetmealDish>setmealDishList= setmealDto.getSetmealDishes();
                     setmealDishList= setmealDishList.stream().map((e)->{
                              e.setSetmealId(setmealDto.getId());
                              return  e;
                          })  .collect(Collectors.toList());
                     setMealDishService.saveBatch(setmealDishList);
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId,ids).eq(Setmeal::getStatus,1);
          Long counts= count(lambdaQueryWrapper);
          if(counts>0)
          {
              throw new CustomException("有套餐正在售卖中，不能删除");
          }
            removeBatchByIds(ids);
          LambdaQueryWrapper<SetmealDish>lambdaQueryWrapper1=new LambdaQueryWrapper<>();
          lambdaQueryWrapper1.in(SetmealDish::getSetmealId,ids);
          setMealDishService.remove(lambdaQueryWrapper1);

    }
}
