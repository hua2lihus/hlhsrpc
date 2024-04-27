package com.hlhs.example.consumer;

import com.hlhs.example.common.model.User;
import com.hlhs.example.common.service.UserService;
import com.hlhs.hlhsrpc.proxy.ServiceProxyFactory;

/**
 * 消费者示例
 */
public class EasyComsumerExample {
    public static void main(String[] args) {
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//        //静态代理
//        UserService userService =new UserServiceStaticProxy();
        User user =new User();
        user.setName("张超");
        //调用
       User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == 空");
        }
//        long number = userService.getNumber();
//        System.out.println(number);
    }

}
