package com.hunny.reijiproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hunny.reijiproject.entity.Category;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author QiuZhengJie
 * @date 2022/5/10
 */

public interface CategoryService extends IService<Category> {
    /**
       根据id删除分类，删除之前需要进行判断当前分类是否有绑定元素
     * @param ids
     * @return void
     * @author QiuZhengJie

     */
@Transactional()
    public void remove(Long ids);
}
