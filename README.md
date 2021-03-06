# KillMall

- 基于SpringBoot的秒杀商城Demo
- 采用前后端分离的模式进行开发
- 所使用工具为SpringBoot+Mybatis


# 基本项目说明

## 项目结构

现有逻辑实现参考的是常见的秒杀平台业务逻辑，主要运用SpringBoot+Mybatis的方式进行开发。
- 项目目录结构如下：

![秒杀商城](https://raw.githubusercontent.com/TemplarJQ/Resources4Readme/master/pic/20190713/projectStruct.jpg)


- 采用的设计模式为领域分离模式

![领域设计模式](https://raw.githubusercontent.com/TemplarJQ/Resources4Readme/master/pic/20190713/Model.jpg)


## 项目整体参考流程

所参考的整体架构设计样式为：
![架构](https://raw.githubusercontent.com/TemplarJQ/Resources4Readme/master/pic/20190713/mainDesignForKillMall.jpg)

项目设计简易UML图
![UML图](https://raw.githubusercontent.com/TemplarJQ/Resources4Readme/master/pic/20190713/design.jpg)


## 开发笔记

实现逻辑和开发笔记都写在了个人博客里：[开发笔记](https://templarjq.github.io/2019/07/02/SpringBoot%E7%A7%92%E6%9D%80%E5%95%86%E5%9F%8E/#more)

## 项目扩展

### 1.1 问题的发现

- 多商品，多库存，多活动模型如何实现？

- 秒杀活动业务逻辑实现后，如何支撑亿级流量的实现，比如
  
  - 容量问题及其解决，出现在哪里，怎样压测并发现问题？
  - 系统水平扩展，怎么做到支持？redis有用吗？
  - 查询效率低下（很深层次的问题）
  - 活动开始前页面被疯狂刷新（查询次数会很多很大）
  - 库存行锁问题（同一时间只有一个事务可以执行减操作，上百都成问题）
  - 下单操作多，缓慢（策略问题）
  - 浪涌流量如何解决（缓存瞬间失效问题）

### 1.2 思考的解决方式

首先秒杀商城这种模型的业务逻辑的问题就是：读多写少，高并发，资源冲突问题

- 读多写少：采用缓存的方式进行改进
- 高并发：限流，负载均衡（tomcat单体并发200可以，超出500-600就gg），缓存，异步（同步并发请求转异步），队列
- 资源冲突：数据库锁，分布式锁，其他原子操作，乐观锁，悲观锁，redis，decr，原子操作

对于可能出现的问题

- 容量问题测试采用JMeter等工具进行压力测试，观察是否有超卖等问题
- redis之所以能解决高并发的原因是它可以直接访问内存，而以往我们用的是数据库(硬盘),提高了访问效率,解决了数据库服务器压力。
- 查询效率的提升要思考，问题是查询造成的吗？如果是如何优化sql语句，加索引的作用？
- 页面刷新问题就要防刷，限流或者验证码形式

- 多级缓存（redis缓存，本地缓存，静态资源cdn，动态请求，页面静态化）
- 构建分布式（nginx反向代理，分布式会话管理）
- 对于浪涌可以采用（秒杀令牌，秒杀大闸，秒杀泄洪）


# 高级项目优化过程

## 所使用工具

在项目的修改过程中，基本上圈定了几种工具和大致版本，预计改进时会不断附加上去：
- Intellij IDEA 2017.3
- 阿里云ESC 操作系统 16.04.6 LTS
- Mysql 5.7.25 数据库
- Redis 4.0.1 缓存
- 消息队列 RocketMQ 4.5

### 逐步改进

- 加入项目云端测试：[云端部署](https://github.com/TemplarJQ/KillMall/issues/4#issue-467684642)
- 加入Jmester性能测试：[性能测试](https://github.com/TemplarJQ/KillMall/issues/5#issue-467693466)
