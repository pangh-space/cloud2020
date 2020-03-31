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