<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">WeCodingTool</h1>
<h4 align="center">Spring Boot/Spring Cloud 快速开发工具包</h4>

## 平台简介

* 采用前后端分离的模式，微服务版本前端(基于 [WeCoding-Vue](https://github.com/coding-hui/wecoding-vue.git))。
* 后端采用Spring Boot、Spring Cloud & Alibaba。
* 注册中心、配置中心选型Nacos，权限认证使用Redis。
* 流量控制框架选型Sentinel，分布式事务选型Seata。

## 系统模块

~~~
top.wecoding   
├── wecoding-bom                // 依赖聚合模块        
├── wecoding-core               // 核心通用模块      
├── wecoding-database           // 数据库依赖         
├── wecoding-starter-auth       // 认证信息模块
├── wecoding-starter-boot       // spring boot 启动聚合模块
├── wecoding-starter-caffeine   // caffeine 缓存封装
├── wecoding-starter-cloud      // 微服务组件封装
├── wecoding-starter-jwt        // Jwt Token 封装
├── wecoding-starter-log        // 日志记录
├── wecoding-starter-mongo      // mongodb 封装
├── wecoding-starter-mybatis    // mybatis 配置
├── wecoding-starter-oss        // oss 存储模块
├── wecoding-starter-redis      // 通用 redis 配置
├── wecoding-starter-security   // 权限校验
├── wecoding-starter-swagger    // 接口文档
├── wecoding-starter-test       // 系统测试依赖
├── wecoding-starter-web        // web 配置
├──pom.xml                      // 项目父 pom
~~~

## 在线体验

- wecoding/1234565
- 演示地址：http://wecoding.top
- 文档地址：开发中

## 项目地址

- [wecoding](https://gitee.com/coder-hui/wecoding.git) - 单体版本
- [wecoding-cloud](https://gitee.com/coder-hui/wecoding-cloud.git) - 微服务版本
- [wecoding-vue](https://gitee.com/coder-hui/wecoding-vue.git) -
  项目前端，基于[vben-admin](https://anncwb.github.io/vue-wecoding-admin/)

## 如何贡献

非常欢迎你的加入！[提一个 Issue](https://github.com/coding-hui/wecoding-tool/issues) 或者提交一个 Pull Request。

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