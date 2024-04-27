package com.hlhs.hlhsrpc.bootstrap;

import com.hlhs.hlhsrpc.RpcApplication;
import com.hlhs.hlhsrpc.config.RegistryConfig;
import com.hlhs.hlhsrpc.config.RpcConfig;
import com.hlhs.hlhsrpc.model.ServiceRegisterInfo;
import com.hlhs.hlhsrpc.model.ServicerMetaInfo;
import com.hlhs.hlhsrpc.registry.LocalRegister;
import com.hlhs.hlhsrpc.registry.Registry;
import com.hlhs.hlhsrpc.registry.RegistryFactory;
import com.hlhs.hlhsrpc.server.tcp.VertxTcpServer;

import java.util.List;

public class ProviderBootstrap {

    public  static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList){
        // RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        // 注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            String serviceName = serviceRegisterInfo.getServiceName();
            // 本地注册
            LocalRegister.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServicerMetaInfo serviceMetaInfo = new ServicerMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + " 服务注册失败", e);
            }
        }

        // 启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
