package com.hlhs.hlhsrpc.config;

import lombok.Data;

/**
 * RPC 注册中心配置
 */
@Data
public class RegistryConfig {
    /**
     * 注册中心类型，默认 etcd
     */
    private String registry = "etcd";
    /**
     * 注册中心地址，默认 http://localhost:2379
     */
    private String address = "http://47.93.39.10:2379";
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     *  连接超时时间，默认 10 秒
     */
    private Long timeout = 10000L;
}
