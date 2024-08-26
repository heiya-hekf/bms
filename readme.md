# BMS图书管理系统（Book Management System）

## 项目介绍

前后端分离的图书管理系统项目。

后端使用Java+SpringBoot+MyBatis+MySQL

前端使用Vue+Axios+Element UI

## 环境介绍

| 名称      | 描述                     |
| --------- |------------------------|
| Java版本  | JDK 1.8.0              |
| IDE工具   | Intellij IDEA 2021.3.2 |
| 构建工具  | Maven 3.6.3            |
| Web服务器 | SpringBoot内嵌自带Tomcat容器 |
| 数据库    | MySQL 5.7.37           |

## 搭建步骤

### 后端程序

1.数据库导入，将`bms.sql`文件导入并运行。

2.打开项目，打开`src/main/resources/application-dev.yml`，修改数据库和Redis的配置文件。

3.打开项目，找到redis文件夹运行redis-service.exe文件，运行本地redis缓存中间件。

4.运行项目

5.访问接口文档，本地配置环境为例访问：http://localhost:6001/bms/doc.html；
访问swagger接口文档（ui不友好不建议）：http://localhost:6001/bms/swagger-ui.html



管理员账号`admin`，密码`admin`

读者账号`hekf`，密码`123456`


## 联系方式
* WeChat：`feng_675644883`
* QQ：`675644883`
* 邮箱：`hekaifeng33@163.com`





