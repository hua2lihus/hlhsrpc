package com.hlhs.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.hlhs.example.common.model.User;
import com.hlhs.example.common.service.UserService;
import com.hlhs.hlhsrpc.model.RpcRequest;
import com.hlhs.hlhsrpc.model.RpcResponse;
import com.hlhs.hlhsrpc.serializer.JdkSerializer;
import com.hlhs.hlhsrpc.serializer.Serializer;

import java.io.IOException;

/**
 * 静态代理
 */
public class UserServiceStaticProxy implements UserService {

    @Override
    public User getUser(User user) {
        //指定序列化器
        Serializer serializer = new JdkSerializer();
        RpcRequest rpcRequest = RpcRequest.builder().serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            // try(...) 这个写法可以不用在finally里面关闭一些流，httpClient等（实现AutoCloseable接口的实例）操作
            try(HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()){
                result=httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();

        } catch (IOException e) {
           e.printStackTrace();
        }

        return null;
    }
}
