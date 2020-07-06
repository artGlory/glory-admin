# Glory-Admin

**这是一个基于springboot2.1.9.RELEASE 和[vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)搭建的后台框架；**

[github地址](https://github.com/artGlory/glory-admin)

[gitee地址](https://gitee.com/artglory/glory-admin.git)

* [Glory\-Admin](#glory-admin)
    * [特点](#%E7%89%B9%E7%82%B9)
    * [技术说明](#%E6%8A%80%E6%9C%AF%E8%AF%B4%E6%98%8E)
    * [演示](#%E6%BC%94%E7%A4%BA)
    * [数据库安装](#%E6%95%B0%E6%8D%AE%E5%BA%93%E5%AE%89%E8%A3%85)
    * [项目启动](#%E9%A1%B9%E7%9B%AE%E5%90%AF%E5%8A%A8)
    * [权限设计](#%E6%9D%83%E9%99%90%E8%AE%BE%E8%AE%A1)
    * [Maven使用<a href="https://maven\.apache\.org/guides/introduction/introduction\-to\-dependency\-mechanism\.html" rel="nofollow">BOM</a>管理](#maven%E4%BD%BF%E7%94%A8bom%E7%AE%A1%E7%90%86)
    * [项目结构](#%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84)
    * [数据缓存层cacheDao](#%E6%95%B0%E6%8D%AE%E7%BC%93%E5%AD%98%E5%B1%82cachedao)
    * [日志记录](#%E6%97%A5%E5%BF%97%E8%AE%B0%E5%BD%95)
    * [分库分表](#%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8)
### 特点

1. 数据库使用分库分表策略，可以进阶为分布式数据存储；
2.  后台管理使用基于角色的权限管理

### 技术说明

| 项目       | 技术                                                      |
| ---------- | --------------------------------------------------------- |
| 后端技术   | springboot & Redis                                        |
| 前端技术   | Element UI & axios & iconfont & permission control & lint |
| 数据库技术 | mysql(**分库分表**，分布式数据库)                         |

### 演示

![](https://github.com/artGlory/IMG/blob/master/glory-admin/GloryAdmin.gif)

### 数据库安装

本项目使用mysql数据库,可以使用数据库脚本创建2个数据库 *multi_module_db*  *multi_module_db_01*

![](https://github.com/artGlory/IMG/blob/master/glory-admin/mysql01.jpg)

![](https://github.com/artGlory/IMG/blob/master/glory-admin/mysql02.jpg)

数据库进阶，使用[Flyway](https://flywaydb.org/)做数据库的版本控制

![](https://github.com/artGlory/IMG/blob/master/glory-admin/flyway01.jpg)

### 项目启动

后台启动，使用28081端口

![](https://github.com/artGlory/IMG/blob/master/glory-admin/backend-start.jpg)

前端启动，使用9523端口

![](https://github.com/artGlory/IMG/blob/master/glory-admin/fronend-start.jpg)

### 权限设计

1. 用户登陆获取token，存储到本地（adminLogin）

2. 用户发送token获取用户信息和权限信息，存储到store里面。由于F5会导致store丢失，因此前端请求添加了拦截器，如果没有用户信息和权限信息就重新获取用户信息和权限（getAdminInfo）

   ![](https://github.com/artGlory/IMG/blob/master/glory-admin/login01.jpg)
   ![](https://github.com/artGlory/IMG/blob/master/glory-admin/login02.jpg)

3. 这里返回的是用户所有的权限而不是角色，用户动态生成前端路由

   asyncRoutes为动态生成的权限，如果用户的权限和路由的权限对应，则显示；

   ![](https://github.com/artGlory/IMG/blob/master/glory-admin/route01.jpg)

### Maven使用[BOM](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)管理

使用**Maven继承**管理项目依赖。Modules里面通过**dependencyManagement**引入依赖并指定版本，子项目继承Modules,引入依赖不需要指定版本

![](https://github.com/artGlory/IMG/blob/master/glory-admin/bom01.jpg)

### 项目结构

1. common处理数据库操作和事务操作

2. admin只做controller，用于处理用户请求和后台业务之间的转发。（why这样设计呢？）because有些中间件系统需要用RPC框架做请求转发，because有些机密系统不屑于用springMVC而是选择vertx自主开发请求层。

 ![](https://github.com/artGlory/IMG/blob/master/glory-admin/back01.jpg)

### 数据缓存层cacheDao

由于内存和磁盘的操作根本不是一个量级的，所有在大的项目中都需要内存型的缓冲层，将磁盘数据缓存到内存中。数据缓存层用于缓存整个数据层的数据，加速站点访问速度。本项目使用Redis做数据缓存层。

![](https://github.com/artGlory/IMG/blob/master/glory-admin/framework01.jpg)

### 日志记录

全局日志处理

![](https://github.com/artGlory/IMG/blob/master/glory-admin/ResponseLog01.jpg)

### 分库分表

本项目使用[sharding JDBC](https://shardingsphere.apache.org/document/current/cn/overview/#shardingsphere-jdbc)处理数据库的分库分表

通常项目都只有一个数据库，国内用的比较多的是阿里云的[druid](https://github.com/alibaba/druid)做数据库的连接池。本项目使用mysql,druid,sharding JDBC。数据分库分片的原理，在程序里面维护**多个数据库连接池，每个数据库连接池对应一个数据库。**分库分表使用**基于 XA 协议的两阶段事务**处理。配置路径**com.spring.common.config.shardingJDBC**











