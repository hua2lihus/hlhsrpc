package com.hlhs.hlhsrpc.bootstrap;

import com.hlhs.hlhsrpc.RpcApplication;

public class ConsumerBootstrap {
    /**
     * 初始化
     */
    public static void init() {
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }

}
