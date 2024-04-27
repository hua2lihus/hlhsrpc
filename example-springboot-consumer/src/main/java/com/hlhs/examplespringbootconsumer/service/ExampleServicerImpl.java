package com.hlhs.examplespringbootconsumer.service;

import com.hlhs.example.common.model.User;
import com.hlhs.example.common.service.UserService;
import com.hlhs.hlhsrpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServicerImpl {
    @RpcReference
    private UserService userService;


    public void test(){
        User user = new User();
        user.setName("张三1");

        User user1 = userService.getUser(user);
        System.out.println(user1.getName());
    }
}
