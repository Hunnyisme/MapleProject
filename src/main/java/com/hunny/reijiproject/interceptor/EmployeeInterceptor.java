package com.hunny.reijiproject.interceptor;

import com.alibaba.fastjson.JSON;
import com.hunny.reijiproject.common.BaseContext;
import com.hunny.reijiproject.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author QiuZhengJie
 * @date 2022/5/9
 * @Since:
 */
@Component
@Slf4j
public class EmployeeInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器已启动");
        if(request.getSession().getAttribute("employee")!=null)
        {
//            long id=Thread.currentThread().getId();
//            log.info("线程id为:{}",id);
            Long empId=(long)request.getSession().getAttribute("employee");
            BaseContext.setCurentId(empId);
            return  true;
        }
        if(request.getSession().getAttribute("user")!=null)
        {
//            long id=Thread.currentThread().getId();
//            log.info("线程id为:{}",id);
            Long userId=(long)request.getSession().getAttribute("user");
            BaseContext.setCurentId(userId);
            return  true;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
