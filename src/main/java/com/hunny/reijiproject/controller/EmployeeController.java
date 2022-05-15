package com.hunny.reijiproject.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hunny.reijiproject.common.R;
import com.hunny.reijiproject.entity.Employee;
import com.hunny.reijiproject.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){

        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    /**
       
     * @param request
     * @param employee 
     * @return com.hunny.reijiproject.common.R<java.lang.String>
     * @author QiuZhengJie
            
     */ 
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee)
    {
        log.info("新增员工信息{}",employee.toString());
        String password=DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
        employee.setPassword(password);
//         employee.setCreateTime(LocalDateTime.now());
//         employee.setUpdateTime(LocalDateTime.now());
//        Long empId=(Long) request.getSession().getAttribute("employee");
//         employee.setCreateUser(empId);
//         employee.setUpdateUser(empId);

        employeeService.save(employee);


        return R.success("新增员工成功");
    }
    /**
     *分页查询的执行过程：
     * 1、页面发送ajax请求，将分页查询参数(page、pagesize、name)提交到服务端.
     * 2、服务端Controller接收页面提交的数据并调用Service查询数据
     * 3、 Service调用Mapper操作数据库，查询分页数据
     * 4、Controller将查询到的分页数据响应给页面
     * 5、页面接收到分页数据并通过Elementul的Table组件展示到页面上
     */
    @GetMapping("/page")
    public R<Page> seletebyPage(Integer page,Integer pageSize,String name)
    {
        log.info("请求参数:{},{},{}",page,pageSize,name);
        Page<Employee> page1=new Page<>(page,pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        lambdaQueryWrapper.orderByDesc(Employee::getCreateTime);
        employeeService.page(page1,lambdaQueryWrapper);
        return  R.success(page1);
    }
    /**

     * @param request  1111
     * @param employee 22222
     * @return com.itheima.reggie.common.R<java.lang.String>
     * @author QiuZhengJie

     */
    @PutMapping
    public R<String> updatebyId(HttpServletRequest request,@RequestBody Employee employee)
    {
        long id=Thread.currentThread().getId();
        log.info("线程id:{}",id);
        log.info(employee.toString());
//        long empId=(long) request.getSession().getAttribute("employee");
//             employee.setUpdateUser(empId);
//             employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("修改成功");
    }
//          @PostMapping("/test")
//          public void test(@RequestBody Employee employee)
//          {
//              System.out.println(employee.getId());
//
//          }
    /**

     * @param id

     * @author QiuZhengJie

     */
    @GetMapping("/{id}")
    public R<Employee> getbyId( @PathVariable Long id)
    {
        log.info("根据id查询记录");
        Employee employee=employeeService.getById(id);
        if(employee!=null)
            return R.success(employee);

        return R.error("没有查询到对应员工信息");
    }


}
