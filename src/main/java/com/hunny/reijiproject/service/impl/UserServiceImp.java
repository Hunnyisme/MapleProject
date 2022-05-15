package com.hunny.reijiproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hunny.reijiproject.entity.User;
import com.hunny.reijiproject.mapper.UserMapper;
import com.hunny.reijiproject.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author QiuZhengJie
 * @date 2022/5/13
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User>implements UserService {

}
