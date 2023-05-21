# 枫叶管理系统

#### 介绍
![image](https://gitee.com/qiusss/drawing-bed/raw/master/MapleLeafTextBig.png)
![image](https://gitee.com/qiusss/drawing-bed/raw/master/tablogo2.png)

枫叶信息管理系统由客户端和管理端组成。客户端页面适配手机屏幕尺寸，提供点餐、订单查询、利用阿里云短信服务实现
验证码发送功能。

![image](https://gitee.com/qiusss/drawing-bed/raw/master/indexusr.png)
![image](https://gitee.com/qiusss/drawing-bed/raw/master/loginusr.png)

客户端可供用户下单，查看最近订单等数据。

![image](https://gitee.com/qiusss/drawing-bed/raw/master/userdetail.png)
***********************************************
后台管理端可以管理员工数据，添加商品信息、查看用户订单等，如下图展示：

登陆界面：
![image](https://gitee.com/qiusss/drawing-bed/raw/master/backendlogin.png)

首页：
![image](https://gitee.com/qiusss/drawing-bed/raw/master/backendindex.png)

商品信息：
![image](https://gitee.com/qiusss/drawing-bed/raw/master/dish.png)

#### 技术栈
1.前端采用 vue+elementUI+ajax
2.后端采用springboot+mybatis-plus+radis缓存搭建，认证采用拦截器，后续可更新为shiro。
3.采用了全局异常拦截器。

静态资源获取请看另一仓库：
https://gitee.com/qiusss/maple-leaf-front-end-resources


#### 参与贡献

1.QZJ 前后端代码编写
2.ZSM logo设计



