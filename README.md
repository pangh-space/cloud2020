# SpringCloud学习笔记
本项目是根据尚硅谷周阳老师的SpringCloud2020，纯手工编写出来的代码，欢迎大家fork学习

#####  一、 基于Eureka注册中心搭建的微服务

1. cloud-provider-payment8001和cloud-provider-payment8002 是服务提供者  
    - 8001和8002的application.yml 配置文件中，配置了连接数据库信息，连接的单节点的注册中心Eureka和集群节点的Eureka
2. cloud-eureka-server7001和cloud-eureka-server7002服务注册中心  
    - 7001和7002 配置了单节点的集群以及互相守望的集群配置，在application.yml 中体现
    - 在application.yml 中还有关闭和开启Eureka的自我保护方式
3. cloud-consumer-order80组要是作为消费端调用cloud-provider的服务
    - 在application.yml 配置文件中，将自己注册进Eureka
    - 使用RestTemplate 同时启用负载均衡策略调用服务提供在，在config 包中使用loadbalance 注解开启负载均衡
    - 实现了自定义的负载均衡算法

#####   二、 基于Zookeeper搭建的微服务

1. 在自己机器搭建Zookeeper集群或者单机节点，具体网址可以参考如下网址：

   [Mac下搭建Zookeeper集群环境](https://blog.csdn.net/u014535952/article/details/101232382/)

2. cloud-provider-payment8004 基于Zookeeper搭建的服务提供方

3. cloud-consumerzk-order80 调用Zookeeper 搭建的服务提供者cloud-provider-payment8004

#####   三、微服务调用组件及服务熔断、降级等处理组件

1. cloud-provider-hystrix-payment8001基于Hystrix搭建的带有服务降级处理的服务提供者
2. cloud-consumer-feign-order80 基于OpenFeign 调用服务提供者的微服务客户端
   - 在项目中创建Service接口，直接调用服务提供方提供的Controller接口
   - 使用@FeignClient(value = "CLOUD-PAYMENT-SERVICE") 注解，value值为服务提供方在注册中心的服务名称
3. cloud-consumer-hystrix-order80 基于Hystrix 实现客户端的服务熔断和降级处理的调用

#####  四、SpringCloud微服务网关GateWay

1. cloud-gateway-gateway9527 单独创建网关项目
   - 需要在application.yml 中配置discover.locator.enable=true 开启从注册中心动态创建路由功能，利用微服务名称进行负载均衡路由
   - routs 上可以配置predicates路由，predicates 有多重路由规则，可以从网络搜索参考
   - filters 主要是配置过滤器，SpringCloudGateWay自带很多中，我们主要还是使用自定义的Filter
     - 创建一个MyFilter类，实现GlobalFilter 和 Ordered 接口
     - 方法filter 中实现具体的业务控制
     - 方法getOrder是过滤器加载的顺序，数字越小，优先级越高

#####  五、SpringCloud配置中心及消息总线

######  1. 配置中心

1. 创建cloud-config-center3344服务，用于获取GitHub 仓库的配置文件信息。作为配置中心的leader存在
2. 创建cloud-config-client3355服务，当做配置中心的client 存在。
   - 3355 配置文件需要创建bootstrap.yml ，加载优先级要比appliation.yml配置文件高
   - GitHub仓库修改了配置文件以后，3344可以实时的读取到配置文件的修改。但是3344读不到
   - 在3355 yml 配置文件中需要增加暴露监控端点配置，Controller中需要增加@RefreshScope注解
   - 运维修改以后，需要手动通知3355 更新配置文件，使用命令：crul -X POST “http://localhost:3355/actuator/refresh"

######  2. 消息总线

1. 创建cloud-config-client3366服务，同时修改3344、3355、3366，pom 中添加消息总线GAV。yml 中添加连接RabbitMQ服务器用户名密码
2. 3344 要开启Bus刷新配置断点
3. 3355和 3366 要暴露监控端点
4. 使用命令 curl -X POST http://localhost:3344/actuator/bus-refresh ，同时所有客户端，更新配置
5. 使用命令curl -X POST http://localhost:3344/actuator/bus-refresh/**config-client:3355**，通知部分客户端，更新配置
   - 备注：加粗部分为微服务在 Eureka的名称+端口号

#####  六、SpringCloudStream-消息驱动

######  Stream主要屏蔽了各个消息中间件的差异，统一了消息的编程模型。

1. 创建cloud-stream-rabbitmq-provider8801 消息的生产者，用于生产消息到RabbitMQ 服务器
2. 创建cloud-stream-rabbitmq-consumer8802 消息的消费者，用于测试8801生产的消息
3. 创建cloud-stream-rabbitmq-consumer8803 消息的消费者，主要是为了模拟重复消费问题。
   - 如果8802和8803 不在同一个分组，那么就会产生消息的重复消费问题
   - 可以使用 bindings-input-group 标签，定义当前服务的分组名称，如果在同一个组就不会出现重复消费的问题
   - 如果8802 去掉分组名称，8803 不去掉分组名称。那么两台微服务都停掉，然后8801发送消息。这时候重启8802，那么8802 将不会消费到已经错过的消息。但是重启8803就会消费到刚刚丢失的消息。这样就证明了消息服务器默认会持久化消息

#####  七、SpringCloud Sleuth 分布式请求链路跟踪和zipkin

######  首先需要下载zipkin jar包，下载地址：[zipkin.jar](https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/2.12.9/) ,下载完成以后，可以使用 java -jar ***.jar 启动服务。然后可以键入 localhost:9411/zipkin 访问查看平台

1. 在cloud-provider-payment8001 中添加spring-cloud-starter-zipkin GAV坐标，然后在yml 配置文件中配置相关信息，Controller中添加测试 zipkin 的方法
2. 在cloud-consumer-order80 中添加同样的配置，Controller中也添加请求方法。在方法中注意，不能使用restTemplate 通过Eureka 中的微服务名称调用。因为在这个Controller中也做了自定义个负载均衡策略配置。所以，如果要请求的话，要么写死路径，要么使用自定义负载均衡策略请求。否则会报错，找不到微服务名称。
3. 最后在浏览器的web界面就可以看到响应的链路调用情况

#####   八、SpringCloud Alibaba Nacos服务注册和配置中心

######  Nacos 可以替代Eureka作为服务的注册中心，并且使用起来要比Eureka方便，不用我们自己单独创建项目启动。Nacos 支持 CP和AP切换配置，从服务的角度，更优于其他的注册中心。

1. 首先从[Nacos官网](https://nacos.io/zh-cn/index.html) 下载安装包，然后安装到Linux 服务器
2. 只有使用命令 ./startup.sh 命令启动Nacos，我们会看到管理界面。默认用户名和密码为 ：nacos/nacos
3. 之后创建项目cloudalibaba-provider-payment9001 服务提供者和 9002 服务提供者，配置相关信息，会在Nacos 管理界面看到我们注册到Nacos的两个集群实例
4. 然后创建cloudalibaba-consumer-nacos-order83 服务消费者，通过Nacos 注册中心，调用 9001和9002。可以配置Rabbion ，Nacos 默认对接了Rabbion的负载均衡机制
5. Nacos 配置中心，创建cloudalibaba-config-nacos-client3377。添加配置文件bootstrap.yml 和 application.yml 配置Nacos 配置中心的文件名称，通过代码读取到配置中心，配置文件的内容。
   1. 配置中心可以使用Data Id作为分组过滤的条件
   2. 可以使用Group作为分组过滤的条件，可以配置生产环境、测试环境和开发环境，不同的配置文件
   3. 可以使用命名空间区分各个环境的配置文件
6. Nacos 持久化配置，Nacos 默认集成了Apache 的derby数据库，如果需要做Nacos 高可用集群，就需要把数据库改为连接MySQL ，当前Nacos 值支持 MySQL 数据库。所以，需要在 nacos的配置文件中，添加MySQL 的连接信息。官网都有介绍
7. Nacos 集群，就需要借助Nginx 做代理，只有所有对 Nacos 的访问，都会通过Nginx 代理转发。所以需要配置Nginx
8. Nacos 本身，也需要在配置文件中配置各个集群环境的IP
9. 配置完成以后，启动三台Nacos 集群，启动Nginx，然后通过 9002 访问配置文件，测试成功...

#####  九、SpringCloud Alibaba Sentinel 实现服务熔断与限流

1. cloudalibaba-sentinel-service8401服务，主要是为了演示Sentinel 的流控规则、流控效果、降级规则、热点规则、以及系统规则，还有Sentinel 的配置持久化到Nacos相关配置
2. cloudalibaba-provider-payment9003和9004 创建了服务的提供则，注册到Nacos 中
3. cloudalibaba-consumer-nacos-order84 通过Rabbion 负载均衡消费9003和9004 的服务
   - 项目中配置了fallback和blockHandler ，通过实验证明这两种异常是可以同时存在的
   - 配置了通过Rabbion负载均衡调用服务提供者的方式
   - 配置了通过OpenFeign 接口的方式调用服务提供者，以及服务熔断的处理方式



