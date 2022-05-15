package com.hunny.reijiproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hunny.reijiproject.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author QiuZhengJie
 * @date 2022/5/14
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
