package com.hlhs.example.provider;

import com.hlhs.example.common.service.UserService;
import com.hlhs.hlhsrpc.RpcApplication;
import com.hlhs.hlhsrpc.config.RegistryConfig;
import com.hlhs.hlhsrpc.config.RpcConfig;
import com.hlhs.hlhsrpc.model.ServicerMetaInfo;
import com.hlhs.hlhsrpc.registry.LocalRegister;
import com.hlhs.hlhsrpc.registry.Registry;
import com.hlhs.hlhsrpc.registry.RegistryFactory;
import com.hlhs.hlhsrpc.server.HttpServer;
import com.hlhs.hlhsrpc.server.VertxHttpServer;
import com.hlhs.hlhsrpc.server.tcp.VertxTcpServer;

/**
 * 简单服务提供示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        RpcApplication.init();

        // 注册服务
        LocalRegister.register(UserService.class.getName(),UserServiceImpl.class);

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry instance = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServicerMetaInfo servicerMetaInfo = new ServicerMetaInfo();
        servicerMetaInfo.setServiceName(UserService.class.getName());
        servicerMetaInfo.setServiceAddress(rpcConfig.getServerHost()+":"+rpcConfig.getServerPort());
        servicerMetaInfo.setServiceHost(rpcConfig.getServerHost());
        servicerMetaInfo.setServicePort(rpcConfig.getServerPort());

        try {
            instance.register(servicerMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // 启动web服务
//        HttpServer vertxHttpServer = new VertxHttpServer();
//        vertxHttpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
        //启动tcp服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8080);
    }
}
