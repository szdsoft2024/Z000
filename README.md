数字化平台Z000
===============
当前最新版本： 1.0.0（发布时间：2024-01-03）

[![AUR](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)]()
[![](https://img.shields.io/badge/Author-尚智得-orange.svg)](https://www.szdsoft.cn/about)
[![](https://img.shields.io/badge/version-1.0.0-brightgreen.svg)](https://gitee.com/szdsdp/demo)

## 项目介绍

数字化平台是河北尚智得信息技术有限公司在助力企业数字化转型过程中构建的核心基础设施，平台以业务需求为导向，旨在通过优秀的系统底层架构、先进的软件技术、高效的数据处理能力和灵活的扩展性，助力企业实现业务模式的创新和管理效率的提升。

目前市场上有很多面向企业业务场景的产品，这些产品给企业管理带来便利性的同时，也带来了一系列问题，例如：
- 不同系统的后台管理功能基本一致，却需要重复建设，导致后期运维难度大、维护成本高；
- 不同系统技术标准、规范不统一，项目交付后，涉及外部厂商的系统多不提供源代码，导致企业自主可控性弱、后期扩展维护难度大；
- 系统间需要高度集成，复杂度高，协调难度大，项目实施周期不可控；
- 传统IT公司注重本产品的业务满足与技术实现，多数想法是做一款解决业务问题的好产品，无法从企业IT规划、技术共享、IT自主可控、降本增效的角度去思考。

![技术思路](https://www.szdsoft.cn/gitee/git-tech.png)

综述以上问题，尚智得总结多年项目实施经验，通过对企业管理思想、大型成熟应用软件、主流互联网、市场多元化系统的分析，推出应用系统和技术平台解耦的数字化平台解决方案。数字化平台不仅提供系统管理、流程管理、主数据管理等基础模块功能，还提供开源的模版项目与前后端组件库，支持开发者面向业务快速定制开发与集成。

![技术思路](https://www.szdsoft.cn/gitee/git-overview.png)

业务应用基于 Spring Boot + Spring Cloud + Nacos + Redis + RocketMQ 等微服务架构开发，前端基于 Vue + Element UI
等实现，支持多种数据库Mysql、Oracle、达梦等，也可自选前后端技术框架，数字化平台提供的组件库与业务应用可快速无缝融合，业务应用开发完全基于原生，企业自主可控。

## 平台体验

- 官方网站： [https://www.szdsoft.cn](https://www.szdsoft.cn)
- 在线文档：[https://www.szdsoft.cn/platform/onlineApi/overview](https://www.szdsoft.cn/platform/onlineApi/overview)
- 在线演示 ：  [https://sdp.szdsoft.cn:81](https://sdp.szdsoft.cn:81) 

> 演示系统的登录账号密码：admin/Szd202*4

## 技术支持

使用中遇到问题或者BUG可以在 [Gitee上提Issues](https://gitee.com/szdsdp/z000/issues)

## 源码下载

- 后台项目 ：https://gitee.com/szdsdp/z000
- 前端项目 ：https://gitee.com/szdsdp/z000-web
- 前端组件库项目 ：https://gitee.com/szdsdp/client-web

## 技术选型

### 前端

| 组件                                                                          | 说明      | 版本      |
|-----------------------------------------------------------------------------|---------|---------|
| [Vue](https://cn.vuejs.org/guide/introduction.html)                         | VUE 3   | 3.2.13  |
| [Vue Router](https://router.vuejs.org/zh/)                                  | 路由管理器   | 4.0.3   |
| [Vuex](https://vuex.vuejs.org/)                                             | 状态管理    | 4.0.0   |
| [Element Plus](https://element-plus.gitee.io/zh-CN/component/overview.html) | UI框架    | 2.3.5   |
| [Axios](https://axios-http.com/)                                            | HTTP客户端 | 1.6.7   |
| [Echarts](https://echarts.apache.org/)                                      | 图表框架    | 5.4.3   |
| [JsEncrypt](https://github.com/travist/jsencrypt)                           | 加密库     | 3.3.2   |
| [Lodash](https://www.lodashjs.com/)                                         | 工具库     | 4.17.21 |
| [Nprogress](https://github.com/rstacruz/nprogress)                          | 进度条库    | 0.2.0   |
| [Qiankun](https://qiankun.umijs.org/zh/guide/)                              | 微前端框架   | 2.10.16 |
| [Screenfull](https://www.npmjs.com/package/screenfull)                      | 全屏工具    | 6.0.2   |
| [VkBeautify](https://github.com/vkiryukhin/vkBeautify)                      | 代码格式化   | 0.99.3  |
| [Vue3 Highlightjs](https://npm.io/package/vue3-highlightjs)                 | 代码高亮    | 1.0.5   |
| [VueDraggable](https://sortablejs.github.io/vue.draggable)                  | 拖拽排序    | 4.1.0   |
| [Wangeditor](https://www.wangeditor.com/)                                   | 富文本编辑器  | 5.1.23  |

### 后台

| 框架                                                                    | 说明               | 版本       |
|-----------------------------------------------------------------------|------------------|----------|
| [Spring Boot](https://spring.io/projects/spring-boot)                 | 应用开发框架           | 2.6.3    |
| [Spring Cloud](https://spring.io/projects/spring-cloud/)              | 微服务框架            | 2021.0.1 |
| [Nacos](https://nacos.io/)                                            | 动态服务发现、配置管理和服务管理 | 2.2.0    |
| [MySQL](https://www.mysql.com/cn/)                                    | 数据库服务器           | 8.0.29   |
| [Druid](https://github.com/alibaba/druid)                             | 数据库连接池           | 1.2.15   |
| [MyBatis](https://mp.baomidou.com/)                                   | MyBatis 工具包      | 2.2.2    |
| [Dynamic Datasource](https://dynamic-datasource.com/)                 | 动态数据源            | 3.6.1    |
| [Redis](https://redis.io/)                                            | key-value 数据库    | 6.2.6    |
| [Jedis](https://github.com/redis/jedis)                               | Redis 客户端        | 5.1.0    |
| [Spring Security](https://github.com/spring-projects/spring-security) | Spring 安全框架      | 5.7.11   |
| [Fastjson](https://github.com/FasterXML/jackson)                      | JSON 工具库         | 1.2.83   |
| [Lombok](https://projectlombok.org/)                                  | 消除冗长的 Java 代码    | 1.18.22  |
| [RocketMQ](https://rocketmq.apache.org/)                              | 消息队列             | 2.2.2    |
| [PageHelper](https://pagehelper.github.io/)                           | 分页工具             | 1.4.6    |
| [PoiOoxml](https://poi.apache.org/)                                   | Excel工具类         | 5.2.3    |
| [Itextpdf](http://itextpdf.com/)                                      | Pdf工具类           | 5.5.13.3 |
| [Javax.Mail](https://www.oracle.com/java/technologies/javamail.html)  | 邮件发送             | 1.4.5    |
| [Aliyun](https://www.aliyun.com/product/sms/)                         | 阿里短信平台           | 4.6.0    |
| [SkyWalking](https://skywalking.apache.org/)                          | 分布式应用追踪系统        | 8.12.0   |

## 功能模块

```
├─首页
│  ├─首页（功能模块可定制化）
├─个人工作台
│  ├─我的草稿
│  ├─我的申请
│  ├─我的退回
│  ├─我的待办
│  ├─我的已办
│  ├─我的消息
│  ├─我的评审
│  └─待办设置
│  └─个人任务统计
├─共享工作台
│  ├─任务池总待办
│  ├─任务池总已办
│  ├─任务池个人待办
│  ├─任务池个人已办
│  ├─共享待办统计
│  ├─共享任务统计
│  ├─扫描复审
│  └─运营看板
├─知识管理
│  ├─知识浏览
│  ├─知识管理
│  ├─草稿管理
│  ├─审核管理
│  ├─回收站
│  ├─帮助文档
│  ├─后台配置
│  │  ├─知识目录
├─电子影像
│  ├─影像配置
│  │  ├─业务标识
│  │  ├─业务映射
│  │  ├─OCR配置
│  │  ├─扫描仪配置
│  ├─影像管理
│  │  ├─影像清单
│  │  ├─附件清单
│  ├─影像日志
│  │  ├─OCR识别结果
│  │  ├─OCR调用次数
│  ├─票夹管理
│  │  ├─票夹管理
│  │  ├─发票管理
│  │  ├─配置管理
├─主数据管理
│  ├─组织管理
│  │  ├─公司代码
│  │  ├─成本中心
│  │  ├─利润中心
│  │  ├─业务范围
│  │  ├─功能范围
│  │  ├─原因代码
│  │  ├─控制组
│  │  ├─岗位
│  │  ├─职级
│  │  ├─段
│  ├─基本信息
│  │  ├─会计科目
│  │  ├─商业银行
│  │  ├─联行号
│  │  ├─税率
│  │  ├─货币
│  │  ├─国家
│  │  ├─地区
│  │  ├─计量单位
│  ├─业务伙伴
│  │  ├─供应商
│  │  ├─客户
│  │  ├─银行账户
├─基础管理
│  ├─系统管理
│  │  ├─用户管理
│  │  ├─组织管理
│  │  ├─角色管理
│  │  ├─业务类型
│  │  ├─业务小类
│  │  ├─字段状态
│  │  ├─企业日历
│  │  ├─通知公告
│  ├─开发管理
│  │  ├─开发管理
│  │  │  ├─菜单管理
│  │  │  ├─移动菜单
│  │  │  ├─字典管理
│  │  │  ├─基础字段
│  │  │  ├─应用系统
│  │  │  ├─参数设置
│  │  │  ├─字段增强
│  │  │  ├─客户化字典
│  │  │  ├─日志配置
│  │  ├─程序注册
│  │  │  ├─数据模板
│  │  │  ├─作业程序
│  │  │  ├─业务路由
│  │  │  ├─业务组件
│  │  │  ├─权限对象
│  │  │  ├─号码对象
│  │  │  ├─移动配置
│  │  │  ├─条码类别
│  │  │  ├─图标管理
│  │  │  ├─语言配置
│  │  │  ├─数据库表
│  ├─系统监控
│  │  ├─接口监控
│  │  │  ├─外围系统
│  │  │  ├─业务类型
│  │  │  ├─集成管理
│  │  │  ├─集成监控
│  │  │  ├─异常日志
│  │  ├─服务监控
│  │  │  ├─用户监控
│  │  │  ├─锁监控
│  │  │  ├─Nacos监控
│  │  │  ├─Redis监控
│  │  ├─日志管理
│  │  │  ├─登录日志
│  │  │  ├─操作日志
│  │  │  ├─权限日志
│  │  │  ├─前置日志
│  ├─发票管理
│  │  ├─发票清单
│  │  ├─发票验真
│  │  ├─票据类型
│  │  ├─票据字段
│  ├─流程管理
│  │  ├─流程设计
│  │  ├─流程组
│  │  ├─固化流程用户
│  │  ├─动态流程用户
│  │  ├─动态流程绩效
│  │  ├─任务池用户组
│  │  ├─绩效组
│  │  ├─交通灯
│  │  ├─流程字段
│  │  ├─流程日志
│  │  ├─用户绩效组
│  │  ├─用户日历
│  ├─凭证管理
│  │  ├─生成规则
│  │  ├─凭证清单
│  ├─附件管理
│  │  ├─附件存储
│  │  ├─附件类别
│  │  ├─附件清单
│  ├─定时任务
│  │  ├─定时任务
│  │  ├─任务调度
├─Z000
│  ├─Z000制单
│  ├─Z000报表
└─其他产品功能
   └─请查看生态体验版
   
```

## 系统效果
- 系统登录
![系统登录](https://www.szdsoft.cn/gitee/git-login.png)
- 系统首页
![系统首页](https://www.szdsoft.cn/gitee/git-home.png)
- 我的已办
![我的已办](https://www.szdsoft.cn/gitee/git-done.png)
- 任务池总待办
![任务池总待办](https://www.szdsoft.cn/gitee/git-todos.png)
- 主数据
![主数据](https://www.szdsoft.cn/gitee/git-mdm.png)
- 基础配置
![基础配置](https://www.szdsoft.cn/gitee/git-basic.png)
- 流程引擎
![流程引擎](https://www.szdsoft.cn/gitee/git-workflow.png)
- 定时任务
![定时任务](https://www.szdsoft.cn/gitee/git-task.png)
- 集成监控
![集成监控](https://www.szdsoft.cn/gitee/git-mond.png)
- 知识库
![知识库](https://www.szdsoft.cn/gitee/git-skis.png)
- 电子影像
![电子影像](https://www.szdsoft.cn/gitee/git-seis.png)
- 附件清单
![附件清单](https://www.szdsoft.cn/gitee/git-files.png)
- 多语言
![多语言](https://www.szdsoft.cn/gitee/git-en.png) 
