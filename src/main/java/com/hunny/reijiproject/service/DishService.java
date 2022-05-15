package com.hunny.reijiproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunny.reijiproject.DTO.DishDto;
import com.hunny.reijiproject.entity.Dish;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author QiuZhengJie
 * @date 2022/5/10
 */
public interface DishService extends IService<Dish> {

@Transactional
public void saveWithFlavor(DishDto dishDto);
    @Transactional
public DishDto getByIdWithFlavor(Long id);
    @Transactional
public void updateWithFlavor(DishDto dishDto);

}
