package com.hunny.reijiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunny.reijiproject.common.R;
import com.hunny.reijiproject.entity.Category;
import com.hunny.reijiproject.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author QiuZhengJie
 * @date 2022/5/10
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
/**
   新增分类
 * @param category
 * @return com.hunny.reijiproject.common.R<java.lang.String>
 * @author QiuZhengJie

 */
    @PostMapping
    public R<String> save(@RequestBody Category category)
    {
             log.info("新增分类:{}",category.toString());
            categoryService.save(category);
            return R.success("新增分类成功");
    }
    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize)
    {
        Page<Category>pageInfo=new Page<>(page,pageSize);
        LambdaQueryWrapper<Category>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo,lambdaQueryWrapper);
        return R.success(pageInfo);
    }
   /**
    根据id删除分类
    * @param ids
    * @return com.hunny.reijiproject.common.R<java.lang.String>
    * @author QiuZhengJie

    */
        @DeleteMapping
       public R<String> deletebyId(Long ids)
       {
           log.info("删除分类，id为{}",ids);
           System.out.println(ids);
          categoryService.remove(ids);

           return R.success("删除成功");
       }
       /**
          根据id修改分类信息
        * @param category
        * @return com.hunny.reijiproject.common.R<java.lang.String>
        * @author QiuZhengJie

        */
       @PutMapping
         public R<String> update(@RequestBody Category category)
         {
             categoryService.updateById(category);
             return R.success("修改分类信息成功");
         }
         /**
            根据条件查询分类数据
          * @param category
          * @return com.hunny.reijiproject.common.R<java.util.List<com.hunny.reijiproject.entity.Category>>
          * @author QiuZhengJie

          */
         @GetMapping("/list")
         public R<List<Category>> list(Category category)
         {
              LambdaQueryWrapper <Category>lambdaQueryWrapper=new LambdaQueryWrapper<>();
              lambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
              lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
          List<Category>list=    categoryService.list(lambdaQueryWrapper);
          return  R.success(list);

         }


}
