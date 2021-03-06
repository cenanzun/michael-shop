# 微服务电商

## 一、技术选型
### A. 采用响应式JavaScript框架Vue.js和Nuxt.js框架构建前端

### B. 采用SpringBoot2.x+SpringCloud2.x构建微服务电商系统
#### 1.使用SpringCloudEureka作为注册中心，实现服务治理
#### 2.使用Zuul网关框架管理服务请求入口
#### 3.使用Ribbon实现本地负载均衡器和FeginHttp客户端调用工具
#### 4.使用Hystrix服务保护框架(服务降级、隔离、熔断、限流)
#### 5.使用消息总线Stream RabbitMQ和Kafka
#### 6.微服务API接口安全控制与单点登陆系统CAS+JWT+Oauth2.0
 
### C. 分布式基础设施环境构建
#### 1.分布式任务调度平台XXL-Job 
#### 2.分布式日志采集系统ELK 
#### 3.分布式事务解决方案LCN 
#### 4.分布式锁解决方案Zookeeper、Redis
#### 5.分布式配置中心携程阿波罗 
#### 6.高并发分布式全局ID生成雪花算法
#### 7.分布式Session框架Spring-Session
#### 8.分布式服务追踪与调用链ZipKin  
 
### D.项目运营与部署环境
#### 1.分布式设施环境，统一采用docker安装
#### 2.使用jenkins+docker+k8s实现自动部署 
#### 3.微服务API管理ApiSwagger
#### 4.使用GitLab代码管理 
#### 5.统一采用第三方云数据库
#### 6.使用七牛云服务器对静态资源实现加速
#### 7.使用阿里云OSS存储服务存储静态资源

# 

## 二、功能模块
### A. 用户服务：用户注册与唯一登录，SSO联合(QQ、微信)登录、手机验证码登录，百度AI刷脸登录
### B. 商品服务：商品搜索服务、购物车服务
### C. 短信服务：使用activeMQ支持阿里云的短信服务
### D. 媒资服务：实现HLS协议实现视频资源的实时播放
### E. 页面服务：使用freemarker生成商品详情等静态页面
### F. 订单服务：生成订单功能
### G. 秒杀服务：商品秒杀功能
### H. 聚合支付服务：对接第三方支付宝、微服务支付、银联支付接口聚合支付
### I. 后台服务：为CMS、用户、订单管理系统，大数据用户行为分析系统提供接口
