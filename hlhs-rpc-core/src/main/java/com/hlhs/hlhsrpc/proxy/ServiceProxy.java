package com.hlhs.hlhsrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import com.hlhs.hlhsrpc.RpcApplication;
import com.hlhs.hlhsrpc.config.RpcConfig;
import com.hlhs.hlhsrpc.constant.RpcConstant;
import com.hlhs.hlhsrpc.fault.retry.RetryStrategy;
import com.hlhs.hlhsrpc.fault.retry.RetryStrategyFactory;
import com.hlhs.hlhsrpc.fault.tolerant.TolerantStrategy;
import com.hlhs.hlhsrpc.fault.tolerant.TolerantStrategyFactory;
import com.hlhs.hlhsrpc.loadbalancer.LoadBalanceFactory;
import com.hlhs.hlhsrpc.loadbalancer.LoadBalancer;
import com.hlhs.hlhsrpc.model.RpcRequest;
import com.hlhs.hlhsrpc.model.RpcResponse;
import com.hlhs.hlhsrpc.model.ServicerMetaInfo;
import com.hlhs.hlhsrpc.protocol.*;
import com.hlhs.hlhsrpc.registry.Registry;
import com.hlhs.hlhsrpc.registry.RegistryFactory;
import com.hlhs.hlhsrpc.serializer.JdkSerializer;
import com.hlhs.hlhsrpc.serializer.Serializer;
import com.hlhs.hlhsrpc.serializer.SerializerFactory;
import com.hlhs.hlhsrpc.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 动态代理
 */
public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();

        // 从注册中心获取服务提供者请求地址
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
        ServicerMetaInfo serviceMetaInfo = new ServicerMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        List<ServicerMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            throw new RuntimeException("暂无服务地址");
        }

        // 负载均衡
        LoadBalancer loadBalancer = LoadBalanceFactory.getInstance(rpcConfig.getLoadBalancer());
        // 将调用方法名（请求路径）作为负载均衡参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", rpcRequest.getMethodName());
        ServicerMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
//            // http 请求
//            // 指定序列化器
//            Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
//            byte[] bodyBytes = serializer.serialize(rpcRequest);
//            RpcResponse rpcResponse = doHttpRequest(selectedServiceMetaInfo, bodyBytes, serializer);
        // rpc 请求
        // 使用重试机制
        RpcResponse rpcResponse;
        try {
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
        } catch (Exception e) {
            // 容错机制
            TolerantStrategy tolerantStrategy = TolerantStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            rpcResponse = tolerantStrategy.doTolerant(null, e);
        }
        return rpcResponse.getData();

    }
}

