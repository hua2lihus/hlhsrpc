package com.hlhs.example.common.service;

import com.hlhs.example.common.model.User;

/**
 * 用户服务service（模拟）
 */
public interface UserService {
    /**
     * 获取用户
     * @param user 用户信息
     * @return 用户信息
     */
    User getUser(User user);

    default short getNumber(){
        return 1;
    }
}
