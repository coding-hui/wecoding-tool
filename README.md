<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">WeCodingCloud</h1>
<h4 align="center">基于 Vue3 和 Spring Boot/Spring Cloud & Alibaba 前后端分离的分布式微服务架构</h4>

## 平台简介

* 采用前后端分离的模式，微服务版本前端(基于 [WeCoding-Vue](git@gitee.com:coder-hui/wecoding-vue.git))。
* 后端采用Spring Boot、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos，权限认证使用Redis。
* 流量控制框架选型Sentinel，分布式事务选型Seata。

## 系统模块

~~~
top.wecoding   
├── wecoding-auth            // 认证中心 [9000]
├── wecoding-gateway         // 网关模块 [8080]
├── wecoding-api             // 接口模块
│       └── wecoding-system-api                          // 系统接口
├── wecoding-common          // 通用模块
│       └── wecoding-common-core                         // 核心模块
│       └── wecoding-common-cache                        // 缓存服务
│       └── wecoding-common-datasource                   // 多数据源
│       └── wecoding-common-feign                        // 权限范围
│       └── wecoding-common-log                          // 日志记录
│       └── wecoding-common-mybatis                      // Mybatis支持
│       └── wecoding-common-rabbit                       // Rabbit支持
│       └── wecoding-common-rocketmq                     // Rocketmq支持
│       └── wecoding-common-security                     // 安全模块
│       └── wecoding-common-swagger                      // 系统接口
│       └── wecoding-common-web                          // Web支持
├── wecoding-service        // 业务模块
│       └── wecoding-gen                                 // 代码生成 [9400]
│       └── wecoding-file                                // 文件服务 [9300]
│       └── wecoding-system                              // 系统模块 [9201]
├── wecoding-visual         // 图形化管理模块
│       └── wecoding-monitor                          // 监控中心 [9100]
├── wecoding-vue            // 前端框架 [80]
├──pom.xml                  // 公共依赖
~~~

## 架构图

<img src="https://oscimg.oschina.net/oscnet/up-82e9722ecb846786405a904bafcf19f73f3.png"/>

## 内置功能

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 部门管理：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3. 岗位管理：配置系统用户所属担任职务。
4. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
5. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6. 字典管理：对系统中经常使用的一些较为固定的数据进行维护。
7. 参数管理：对系统动态配置常用参数。
8. 通知公告：系统通知公告信息发布维护。
9. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. 登录日志：系统登录日志记录查询包含登录异常。
11. 在线用户：当前系统中活跃用户状态监控。
12. 定时任务：在线（添加、修改、删除)任务调度包含执行结果日志。
13. 代码生成：前后端代码的生成（java、html、xml、sql）支持CRUD下载 。
14. 系统接口：根据业务代码自动生成相关的api接口文档。
15. 服务监控：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. 在线构建器：拖动表单元素生成相应的HTML代码。
17. 连接池监视：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 在线体验

- wecoding/1234565
- 演示地址：http://wecoding.top
- 文档地址：开发中

## 更新日志

[CHANGELOG](./CHANGELOG.zh_CN.md)

## 项目地址

- [wecoding](https://gitee.com/coder-hui/wecoding.git) - 单体版本
- [wecoding-cloud](https://gitee.com/coder-hui/wecoding-cloud.git) - 微服务版本
- [wecoding-vue](https://gitee.com/coder-hui/wecoding-vue.git) -
  项目前端，基于[vben-admin](https://anncwb.github.io/vue-wecoding-admin/)

## 如何贡献

非常欢迎你的加入！[提一个 Issue](https://gitee.com/coder-hui/wecoding-cloud/issues) 或者提交一个 Pull Request。

**Pull Request:**

1. Fork 代码!
2. 创建自己的分支: `git checkout -b feat/xxxx`
3. 提交你的修改: `git commit -am 'feat(function): add xxxxx'`
4. 推送您的分支: `git push origin feat/xxxx`
5. 提交`pull request`

## Git 贡献提交规范

- `feat` 增加新功能
- `fix` 修复问题/BUG
- `style` 代码风格相关无影响运行结果的
- `perf` 优化/性能提升
- `refactor` 重构
- `revert` 撤销修改
- `test` 测试相关
- `docs` 文档/注释
- `chore` 依赖更新/脚手架配置修改等
- `workflow` 工作流改进
- `ci` 持续集成
- `types` 类型定义文件更改
- `wip` 开发中