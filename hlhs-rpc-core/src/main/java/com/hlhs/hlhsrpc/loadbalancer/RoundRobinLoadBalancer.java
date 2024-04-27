package com.hlhs.hlhsrpc.loadbalancer;

import com.hlhs.hlhsrpc.model.ServicerMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询负载均衡器
 */
public class RoundRobinLoadBalancer implements LoadBalancer {
    /**
     * 计数器（当前轮询下标）
     */
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public ServicerMetaInfo select(Map<String, Object> requestParams, List<ServicerMetaInfo> servicerMetaInfos) {

        if (servicerMetaInfos.isEmpty()) {
            return null;
        }
        int size = servicerMetaInfos.size();
        if (size == 1) {
            return servicerMetaInfos.get(0);
        }
        int index = currentIndex.getAndIncrement() % size;
        return servicerMetaInfos.get(index);

    }

}
