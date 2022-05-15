package com.hunny.reijiproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hunny.reijiproject.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee>{
}
