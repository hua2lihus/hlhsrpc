package com.hlhs.hlhsrpc.config;

import com.hlhs.hlhsrpc.fault.retry.RetryStrategyKeys;
import com.hlhs.hlhsrpc.loadbalancer.LoadBalancerKeys;
import com.hlhs.hlhsrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * Rpc 框架配置
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "hlhs-rpc";
    /**
     * 版本号
     */
    private String version = "1.0";
    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";
    /**
     * 端口号
     */
    private Integer serverPort = 8080;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;
    /**
     * 模拟调用
     */
    private boolean mock = false;
    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();
    /**
     * 负载均衡器
     */
    private String loadBalancer= LoadBalancerKeys.ROUND_ROBIN;

    /**
     * 重试策略
     */
    private String retryStrategy = RetryStrategyKeys.NO;

}
