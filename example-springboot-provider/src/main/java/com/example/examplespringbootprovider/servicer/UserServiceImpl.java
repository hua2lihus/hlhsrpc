package com.example.examplespringbootprovider.servicer;

import com.hlhs.example.common.model.User;
import com.hlhs.example.common.service.UserService;
import com.hlhs.hlhsrpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("UserServiceImpl输出用户名："+user.getName());
        return user;
    }
}
