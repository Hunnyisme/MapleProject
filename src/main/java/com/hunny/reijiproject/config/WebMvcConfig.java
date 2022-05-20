package com.hunny.reijiproject.config;

import com.hunny.reijiproject.common.JacksonObjectMapper;
import com.hunny.reijiproject.interceptor.EmployeeInterceptor;
import com.hunny.reijiproject.interceptor.UserInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 自定义拦截器
 * @author QiuZhengJie
 * @date 2022/5/9
 * @Since:
 */
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    EmployeeInterceptor employeeInterceptor;
    @Autowired
    JacksonObjectMapper jacksonObjectMapper;
    @Autowired
    UserInterceptor userInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

       registry.addInterceptor(employeeInterceptor).addPathPatterns("/**").excludePathPatterns(
               "/backend/**", "/front/**","/employee/login","/employee/logout","/user/login","/user/sendMsg"
               ,"/welcome","/welcome2","/test"
       );
//           registry.addInterceptor(userInterceptor).addPathPatterns("/**").excludePathPatterns(
//                   "/backend/**", "/front/**","/employee/login","/employee/logout","/user/login","/user/sendMsg"
//                   ,"/welcome","/welcome2"
//           );
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(jacksonObjectMapper);
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }
}
