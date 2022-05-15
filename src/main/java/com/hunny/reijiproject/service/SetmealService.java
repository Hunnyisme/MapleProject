package com.hunny.reijiproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunny.reijiproject.entity.Setmeal;
import com.hunny.reijiproject.DTO.SetmealDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author QiuZhengJie
 * @date 2022/5/10
 */
public interface SetmealService extends IService<Setmeal> {
    /**
       新增套餐，同时保存套餐和菜品关系
     * @param setmealDto
     * @return void
     * @author QiuZhengJie

     */
    @Transactional
public void saveWithDish(SetmealDto setmealDto);
/**
   删除套餐及菜品与套餐的关联信息
 * @param ids
 * @return void
 * @author QiuZhengJie

 */
    @Transactional
    public void removeWithDish(List<Long> ids);
}
