# 花里胡哨高性能可扩展RPC框架
# （hlhs-rpc）

> 简单配置即可使用
## 快速开始


### 引入依赖
### 方式1，
下载代码到本地后，install到本地仓库
```xml
        <dependency>
            <groupId>com.hlhs</groupId>
            <artifactId>hlhs-rpc-spring-boot-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```
### 方式2
无需下载代码
```xml
<dependency>
    <groupId>io.github.hua2lihus</groupId>
    <artifactId>hlhs-rpc-spring-boot-starter</artifactId>
    <version>0.0.1</version>
</dependency>
```
配置文件
```properties
hlhsrpc.name=hlhs-rpc  
hlhsrpc.version=3.0
hlhsrpc.serializer=json
hlhsrpc.mock=false
hlhsrpc.registryConfig.registry=etcd
hlhsrpc.registryConfig.address=http://localhost:2379



```
- name:服务名称
- version:服务版本
- serializer:序列化方式
- mock:是否开启mock
- registry:注册中心类型
- address:注册中心地址


------          maven中心仓库审核中！！！！！！