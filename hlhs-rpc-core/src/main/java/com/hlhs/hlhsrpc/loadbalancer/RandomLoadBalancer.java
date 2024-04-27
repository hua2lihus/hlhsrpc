package com.hlhs.hlhsrpc.loadbalancer;

import com.hlhs.hlhsrpc.model.ServicerMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 随机负载均衡
 */
public class RandomLoadBalancer implements LoadBalancer {
    private final Random random = new Random();

    @Override
    public ServicerMetaInfo select(Map<String, Object> requestParams, List<ServicerMetaInfo> servicerMetaInfos) {
        int size = servicerMetaInfos.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return servicerMetaInfos.get(0);
        }

        return servicerMetaInfos.get(random.nextInt(size));
    }
}
