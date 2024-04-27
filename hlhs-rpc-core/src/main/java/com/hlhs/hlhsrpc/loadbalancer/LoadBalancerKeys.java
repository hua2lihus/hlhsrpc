package com.hlhs.hlhsrpc.loadbalancer;

/**
 * 负载均衡器键名
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";

}
