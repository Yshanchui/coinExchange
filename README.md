# CoinExchange数字货币交易所

【郑重声明】
按照中国国家相关法律政策规定，不得向中国大陆境内公民提供数字资产交易服务，不得提供人民币对数字货币的兑换及支付服务。如果不予配合履行以上职责，造成的一切后果与本人无关。

#### 1、介绍
基于Java开发的货币交易所|BTC交易所|ETH交易所|数字货币交易所|交易平台|撮合交易引擎。本项目基于SpringCloudAlibaba微服务开发，可用来搭建和二次开发数字货币交易所。

#### 2、项目特色
1. 基于内存撮合引擎，比传统基于数据库撮合引擎更快 ；
2. 前后端分离，基于OAuth2.0 + JWT的API授权机制 ；
3. 基于SpringCloud微服务架构，扩展更容易 ;
4. 集成阿里最新的研究成功SpringCloudAlibaba ；
5. MySQL、MongoDB、Redis多种数据存储方式，只为更快 ；
6. Kafka发布订阅消息队列，让订单更快流转 ；
7. Netty秒级实时K-Line推送  ；
8. uni-app“七端”共享 ；
9. 主流币种对接区块链接口齐全，开箱即用 ；
10. 冷热钱包分离，两种提现方式，保证安全



#### 3、技术选型

##### 3.1 后台技术

| 组件                   | 作用                 | 参考网站                                                     |
| ---------------------- | -------------------- | ------------------------------------------------------------ |
| Spring Framework       | 容器                 | <http://projects.spring.io/spring-framework/>                |
| Spring Boot            | 开发脚手架框架       | <https://spring.io/projects/spring-cloud/>                   |
| Spring Cloud           | 微服务框架           | <https://spring.io/projects/spring-boot/>                    |
| Spring Security        | 安全框架             | <https://spring.io/projects/spring-security>                 |
| MyBatis-Plus           | ORM框架              | <https://mp.baomidou.com/>                                   |
| Nacos                  | 服务治理             | https://nacos.io/zh-cn/                                      |
| Sentinel               | 服务保护             | https://sentinelguard.io/zh-cn/                              |
| Seata                  | 分布式事务           | http://seata.io/zh-cn/                                       |
| MyBatis                | ORM框架              | <http://www.mybatis.org/mybatis-3/zh/index.html>             |
| MyBatis Generator      | 代码生成             | <http://www.mybatis.org/generator/index.html>                |
| PageHelper             | MyBatis物理分页插件  | <http://git.oschina.net/free/Mybatis_PageHelper>             |
| Druid                  | 数据库连接池         | <https://github.com/alibaba/druid>                           |
| Mongodb                | 分布式文件存储数据库 | <https://www.mongodb.com/>                                   |
| ZooKeeper              | 分布式协调服务       | <http://zookeeper.apache.org/>                               |
| Redis                  | 分布式缓存数据库     | <https://redis.io/>                                          |
| Redisson               | Redis客户端          | <https://redisson.org/>                                      |
| Jetcache               | 缓存框架             | [https://github.com/alibaba/jetcache](https://github.com/alibaba/jetcache/) |
| RabbitMQ               | 消息队列             | <https://www.rabbitmq.com/>                                  |
| Kafka                  | 消息队列             | <http://kafka.apache.org/>                                   |
| Disruptor              | 并发框架             | <https://lmax-exchange.github.io/disruptor/>                 |
| FastDFS                | 自建分布式文件系统   | <https://github.com/happyfish100/fastdfs>                    |
| Log4J                  | 日志组件             | <http://logging.apache.org/log4j/1.2/>                       |
| Swagger2               | 接口测试框架         | <http://swagger.io/>                                         |
| Lombok                 | 简化编码插件         | <https://projectlombok.org/>                                 |
| Cloud Alibaba OSSQiniu | 云存储               | <https://www.aliyun.com/product/oss/> <http://www.qiniu.com/> |
| FastJson & Gson        | 数据序列化           | <https://github.com/alibaba/fastjson>                        |
| Jenkins                | 持续集成工具         | <https://jenkins.io/index.html>                              |
| Maven                  | 项目构建管理         | <http://maven.apache.org/>                                   |
| Tio                    | 实时推送             | <https://gitee.com/tywo45/t-io>                              |
| Netty                  | 实时推送             | https://netty.io/                                            |
| Akka                   | 异步并发计算         | https://akka.io/                                             |

##### 3.2 前端技术

后台管理系统: Vue + ElementUI + Axios + Xlsx + Showdown + Screenfull

前台系统: Vue + ElementUI + Axios + Stompjs + Zip + vue-i18n

移动全栈: uni-app

#### 4、功能说明

##### 4.1 后台管理

![](https://lzj-coin-exchange-images.oss-cn-shenzhen.aliyuncs.com/gitee-coin-exchange/VCTEY73P4IP822%24%5B1_%25HRFD.png)

##### 4.2 前台系统

![](https://lzj-coin-exchange-images.oss-cn-shenzhen.aliyuncs.com/gitee-coin-exchange/PS4SQI%28UO5UTLU%5B%24%606LYCAO.png)

##### 4.3 移动全栈

![](https://lzj-coin-exchange-images.oss-cn-shenzhen.aliyuncs.com/gitee-coin-exchange/QQ%E5%9B%BE%E7%89%8720210320031834.png)



#### 5、架构设计
