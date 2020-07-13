# Glory-Admin

**GloryAdmin是一个基于springboot2.1.9.RELEASE 和[vue-admin-template](https://github.com/PanJiaChen/vue-admin-template)搭建的后台框架；**

**GloryAdmin使用基于角色的权限管理。角色树是一个以“系统管理员”为根节点的树，权限树是由多个子权限树组成。“系统管理员”拥有所有权限；非系统管理员角色可以查看当前角色和直属下级角色的信息，但只能增删改直属下级的角色的信息（直属下级：A是B的直属下级，则A必须为B的孩子节点）。**



[github地址](https://github.com/artGlory/glory-admin)

[gitee地址](https://gitee.com/artglory/glory-admin.git)



### 技术说明

| 项目     | 技术                |
| -------- | ------------------- |
| 后端项目 | springboot          |
| 前端项目 | Element UI & Vue.js |
| 数据库   | MySQL               |
| 缓存     | Redis               |

### 演示

![](https://github.com/artGlory/IMG/blob/master/glory-admin/GloryAdmin.gif)

### 系统架构

![](https://github.com/artGlory/IMG/blob/master/glory-admin/%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84.jpg)

## 项目启动

### 数据库安装

本项目使用mysql数据库,可以使用数据库脚本创建2个数据库 *multi_module_db*  *multi_module_db_01*

![](https://github.com/artGlory/IMG/blob/master/glory-admin/%E6%95%B0%E6%8D%AE%E5%BA%93%E5%88%9B%E5%BB%BA.jpg)

### 项目启动

后台启动，使用28081端口

![](https://github.com/artGlory/IMG/blob/master/glory-admin/backend-start.jpg)

前端启动，使用9523端口

![](https://github.com/artGlory/IMG/blob/master/glory-admin/fronend-start.jpg)

打开浏览器访问  http://localhost:9523  admin a123456

![](https://github.com/artGlory/IMG/blob/master/glory-admin/%E5%90%8E%E5%8F%B0%E7%99%BB%E9%99%86%E7%95%8C%E9%9D%A2.jpg)

## 数据库

### 为什么要数据拆分？什么是分库分表？什么是分布式数据库？

分库分表或者 sharding 的本质是摩尔定律的失效，将数据集中存储至单一数据节点的解决方案，在性能、可用性和运维成本这三方面已经难于满足互联网的海量数据场景。

单数据库不能支撑现有的业务，因此出现了分库分表，使用多个数据库进行数据存储。分库分表简单理解就是一个篮子里面装的东西有限，影响了查找效率和容量，把篮子里面的东西分成N份，装到不同的篮子里面。从而打破容量限制，提高查询效率。

然后我们说一下分布式数据库，国内比较流行的有腾讯的TDSQL、阿里的OceanBase，PolarDB、华为的GaussDB等。基本上都是**自主研发，强一致高可用、全球部署架构、分布式无限水平扩展、高性能，千亿条记录、数百TB数据上的跨行跨表事务（为祖国点赞）**。分布式数据库隐藏了数据库分库分表的策略，智能的进行数据的分库分表，使用起来就像操作一个数据库一样。

![分布式数据库数据流操作流程图](https://github.com/artGlory/IMG/blob/master/glory-admin/%E5%88%86%E5%B8%83%E5%BC%8F%E6%95%B0%E6%8D%AE%E5%BA%93.jpg)

### [Flyway](https://flywaydb.org/)数据库的版本控制

![](https://github.com/artGlory/IMG/blob/master/glory-admin/flyway01.jpg)

### 数据库缓存层cacheDao

由于内存操作和磁盘操作根本不是一个量级的，所以在大的项目中都需要对 **磁盘型的数据库** 做 **内存型的缓冲层**，将磁盘数据缓存到内存中。数据缓存层用于缓存整个数据层的数据，加速站点访问速度。本项目使用 **AOP技术**、**Redis内存数据库** 做数据缓存层。详细请自行查看代码 com/spring/common/aop/CacheDaoAspect.java

### 分库分表

本项目使用[sharding JDBC](https://shardingsphere.apache.org/document/current/cn/overview/#shardingsphere-jdbc)处理数据库的分库分表。根据业务场景，自行拆分数据。

通常项目都只有一个数据库，国内用的比较多的是阿里云的[druid](https://github.com/alibaba/druid)做数据库的连接池。本项目使用mysql,druid,sharding JDBC。数据分库分片的原理，在程序里面维护**多个数据库连接池，每个数据库连接池对应一个数据库。**分库分表使用**基于 XA 协议的两阶段事务**处理。配置路径**com.spring.common.config.shardingJDBC**

### 数据分片的拆分方式又分为垂直分片和水平分片

垂直拆分：按照业务拆分的方式称为垂直分片，又称为纵向拆分。按照业务将表分布到不同的数据库中，从而将压力分散至不同的数据库。

水平拆分：不关心业务逻辑分类，而是通过某张表的某个字段（或某几个字段），根据某种规则将数据分散至多个库或表中。这里的规则，涉及到的算法，我们称为**分片算法**。

### 常用分片算法（*以下内容取自shardingJDBC文档*）

- 精确分片算法

对应 PreciseShardingAlgorithm，用于处理使用单一键作为分片键的 `=` 与 `IN` 进行分片的场景。需要配合 StandardShardingStrategy 使用。

- 范围分片算法

对应 RangeShardingAlgorithm，用于处理使用单一键作为分片键的 `BETWEEN AND`、`>`、`<`、`>=`、`<=`进行分片的场景。需要配合 StandardShardingStrategy 使用。

- 复合分片算法

对应 ComplexKeysShardingAlgorithm，用于处理使用多键作为分片键进行分片的场景，包含多个分片键的逻辑较复杂，需要应用开发者自行处理其中的复杂度。需要配合 ComplexShardingStrategy 使用。

- Hint分片算法

对应 HintShardingAlgorithm，用于处理使用 `Hint` 行分片的场景。需要配合 HintShardingStrategy 使用。



## 后端

### 权限设计

1. 用户登陆获取token，存储到本地（adminLogin）

2. 用户发送token获取用户信息和权限信息，存储到store里面。由于F5会导致store丢失，因此前端请求添加了拦截器，如果没有用户信息和权限信息就重新获取用户信息和权限（getAdminInfo）

   ![](https://github.com/artGlory/IMG/blob/master/glory-admin/login01.jpg)
   ![](https://github.com/artGlory/IMG/blob/master/glory-admin/login02.jpg)

3. 这里返回的是用户所有的权限而不是角色，用户动态生成前端路由

   asyncRoutes为动态生成的权限，如果用户的权限和路由的权限对应，则显示；

   ![](https://github.com/artGlory/IMG/blob/master/glory-admin/route01.jpg)

### 项目结构

1. common：数据操作，数据缓存，事务操作

2. admin只做controller，用于处理用户请求和后台业务之间的转发。（why这样设计呢？）because有些中间件系统需要用RPC框架做请求转发，because有些机密系统不屑于用springMVC而是选择vertx自主开发请求层。

 ![](https://github.com/artGlory/IMG/blob/master/glory-admin/%E9%A1%B9%E7%9B%AE%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84.jpg)

### Maven使用[BOM](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)管理

使用**Maven继承**管理项目依赖。Modules里面通过**dependencyManagement**引入依赖并指定版本，子项目继承Modules,引入依赖不需要指定版本

![](https://github.com/artGlory/IMG/blob/master/glory-admin/bom01.jpg)

### 日志记录

全局日志处理

![](https://github.com/artGlory/IMG/blob/master/glory-admin/ResponseLog01.jpg)

## 前端



