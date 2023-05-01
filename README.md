# coisini.cn

<p style="text-align: center">
 <a href="https://badgen.net/https/cal-badge-icd0onfvrxx6.runkit.sh/Asia/Shanghai" target="_blank"><img alt="Read the Docs" src="https://badgen.net/https/cal-badge-icd0onfvrxx6.runkit.sh/Asia/Shanghai"></a><br/>
 <a href="https://gitee.com/xiangshaw/coisini" target="_blank"><img alt="Gitee" src="https://img.shields.io/badge/Gitee-coisini-orange?style=social&logo=gitee&colorA=F77234&link=https://gitee.com/xiangshaw/coisini"></a><br/>
 <a href="https://github.com/xiangshaw/coisini" target="_blank"><img alt="GitHub" src="https://img.shields.io/badge/Github-coisini-orange?style=social&logo=github&colorA=F77234&link=https://github.com/xiangshaw/coisini"></a>
 <a href="https://github.com/xiangshaw/coisini" target="_blank"><img alt="stars" src="https://badgen.net/github/stars/xiangshaw/coisini"></a>
 <a href="https://github.com/xiangshaw/coisini" target="_blank"><img alt="release" src="https://badgen.net/github/release/xiangshaw/coisini"></a>
</p>


- 积丝成寸，积寸成尺；尺寸不已，遂成丈匹。
- 首页访问：[https://coisini.cn](https://coisini.cn)

## 一、项目环境搭建

| 名称            | 说明                             | 部署指南                                                     |
| --------------- | -------------------------------- | ------------------------------------------------------------ |
| Centos Stream 9 | Linux操作系统                    | [Centos Stream 9 图文详细安装记录](https://blog.csdn.net/qq_44870331/article/details/129988704) |
| JDK-8u361       | Java开发核心                     | [CentOS jdk-8u361-linux-x64安装配置记录](https://blog.csdn.net/qq_44870331/article/details/129641888) |
| MySQL-8.0.28    | 关系型数据库                     | [Linux stream9 mysql-8.0.28-el7-x86_64.tar包的安装记录](https://blog.csdn.net/qq_44870331/article/details/129713600) |
| Docker-23.0.1   | 应用容器引擎                     | [Centos Stream 9 安装 Docker23.0.1](https://blog.csdn.net/qq_44870331/article/details/129735250) |
| Nacos-2.2.0     | 动态服务发现、配置管理和服务管理 | [基于CentOS Stream 9平台搭建Nacos2.2.0集群以及OpenResty反向代理以及GRPC协议配置](https://blog.csdn.net/qq_44870331/article/details/129828647) |
| Sentinel-1.8.6  | 高可用流控防护                   | [Sentinel1.8.6规则持久化到Nacos2.2.0集群记录](https://blog.csdn.net/qq_44870331/article/details/129886930) |
| Seata-1.6.1     | 解决分布式事务                   | [基于CentOS Stream 9平台部署Seata1.6.1，以及使用NaocsConfig获取数据源记录](https://blog.csdn.net/qq_44870331/article/details/129901685) |
| Redis-6.2.6     | 缓存业务                         | [Linux-Redis 6.2.6安装记录+后台启动+开机自启](https://blog.csdn.net/qq_44870331/article/details/122892553) |

## 二、项目架构

版本对应关系：[https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/版本说明)

| Spring Cloud Version | Spring Cloud Alibaba Version | SpringBoot Version | Nacos Version | Seata Version |
| -------------------- | ---------------------------- | ------------------ | ------------- | ------------- |
| 2021.0.5             | 2021.0.5.0                   | 2.6.13             | 2.2.0         | 1.6.1         |

其它：

| 其它     | 介绍                                                         |
| -------- | ------------------------------------------------------------ |
| 环境平台 | [Centos Stream 9](https://www.centos.org/download/)、[Docker 23.0.1](https://hub.docker.com/search?q=) |
| JDK      | [JDK 8u361](https://www.oracle.com/java/technologies/downloads/) |
| 数据库   | [MySQL 8.0.28](https://dev.mysql.com/downloads/mysql/)、[Redis6.2.6](https://github.com/redis/redis) |
| 其它框架 | [MyBatis-Plus 3.5.3.1](https://github.com/baomidou/mybatis-plus)、[MyBatis-Plus-JOIN 1.4.4.2](https://github.com/yulichang/mybatis-plus-join)、[ip2region v2.0](https://github.com/lionsoul2014/ip2region) |

## 三、项目介绍

技术整合:

- [√] 网关
- [√] 新增SpringSecurity权限控制、修改了common层结构
- [√] 集成日志模块
- [√] IP归属地

项目开发中，敬请期待~
