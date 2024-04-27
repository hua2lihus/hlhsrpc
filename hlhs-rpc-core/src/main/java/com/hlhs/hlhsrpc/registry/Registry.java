package com.hlhs.hlhsrpc.registry;

import com.hlhs.hlhsrpc.config.RegistryConfig;
import com.hlhs.hlhsrpc.model.ServicerMetaInfo;

import java.util.List;

/**
 * 注册中心接口
 */
public interface Registry {
    /**
     * 初始化注册中心
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务
     *
     * @param serviceMenetInfo
     * @throws Exception
     */
    void register(ServicerMetaInfo serviceMenetInfo) throws Exception;

    /**
     * 销毁服务
     *
     * @param serviceMenetInfo
     */
    void unRegister(ServicerMetaInfo serviceMenetInfo);

    /**
     * 服务发现
     * @param serviceKey
     * @return
     */
    List<ServicerMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 心跳检测
     */
    void hearBeat();

    /**
     * 服务销毁
     */
    void destroy();

    /**
     * 监听（消费端）
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);
}
