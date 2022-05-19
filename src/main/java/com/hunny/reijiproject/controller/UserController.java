package com.hunny.reijiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hunny.reijiproject.common.R;
import com.hunny.reijiproject.entity.User;
import com.hunny.reijiproject.service.UserService;
import com.hunny.reijiproject.utils.SMSUtils;
import com.hunny.reijiproject.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**

@author QiuZhengJie
@date 2022/5/13 


*/
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
       发送手机验证码
     <ol>
     <li>获取手机号</li>
     <li>生成随机验证码</li>
     <li>使用阿里云api发送短信</li>
     <li>校验</li>
     </ol>
     * @param user
     * @return com.hunny.reijiproject.common.R<java.lang.String>
     * @author QiuZhengJie

     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session)
    {
        String phone=user.getPhone();
        if(StringUtils.isNotEmpty(phone))
        {
          String code=  ValidateCodeUtils.generateValidateCode4String(4);
          log.info("手机验证码code:{}",code);
            System.out.println("手机号码为:"+phone);
            System.out.println("获取验证码的session:"+session);
            SMSUtils.sendMessage("reiji","",phone,code);

            //将验证码缓存到redis中，设置存活期为5分钟
//             session.setAttribute(phone,code);
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
             return R.success("短信验证码发送成功");
        }

        return R.error("短信验证码发送失败");
    }
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String,String> map,HttpSession session)
    {
        System.out.println("接收到的参数:"+map.toString());
        String phone=map.get("phone");
        String code=map.get("code");
        System.out.println("请求登陆的session："+session);
//        Object codeInSession=session.getAttribute(phone);
        Object codeInSession=redisTemplate.opsForValue().get(phone);
        if(codeInSession!=null&&codeInSession.equals(code)){
            LambdaQueryWrapper<User>lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getPhone,phone);
           User user2= userService.getOne(lambdaQueryWrapper);
            if(user2==null)
            {
                user2=new User();
                user2.setPhone(phone);
                user2.setStatus(1);
                userService.save(user2);

            }
            session.setAttribute("user",user2.getId());
            redisTemplate.delete(phone);
            return R.success(user2);
        }
          return R.error("登陆失败");
    }
}
