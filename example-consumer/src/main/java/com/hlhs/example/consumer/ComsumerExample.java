package com.hlhs.example.consumer;

import com.hlhs.example.common.model.User;
import com.hlhs.example.common.service.UserService;
import com.hlhs.hlhsrpc.bootstrap.ConsumerBootstrap;
import com.hlhs.hlhsrpc.config.RpcConfig;
import com.hlhs.hlhsrpc.proxy.ServiceProxyFactory;
import com.hlhs.hlhsrpc.utils.ConfigUtils;

public class ComsumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("hlhs-szl");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
