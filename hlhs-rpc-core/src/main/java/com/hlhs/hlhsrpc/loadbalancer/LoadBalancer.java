package com.hlhs.hlhsrpc.loadbalancer;

import com.hlhs.hlhsrpc.model.ServicerMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     *
     * @param requestParams
     * @param servicerMetaInfos
     * @return
     */
    ServicerMetaInfo select(Map<String, Object> requestParams, List<ServicerMetaInfo> servicerMetaInfos);
}
