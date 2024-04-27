package com.hlhs.hlhsrpc.registry;

import com.hlhs.hlhsrpc.model.ServicerMetaInfo;

import java.util.List;

public class RegistryServiceCache {
    /**
     * 服务缓存
     */
    List<ServicerMetaInfo> serviceCache;

    /**
     * 写缓存
     *
     * @param newServiceCache
     * @return
     */
    void writeCache(List<ServicerMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     *
     * @return
     */
    List<ServicerMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    void clearCache() {
        this.serviceCache = null;
    }
}
