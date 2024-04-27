package com.hlhs.hlhsrpc.loadbalancer;

import com.hlhs.hlhsrpc.registry.EtcdRegistry;
import com.hlhs.hlhsrpc.registry.Registry;
import com.hlhs.hlhsrpc.spi.SpiLoader;

public class LoadBalanceFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     *  获取注册中心实例
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }
}
