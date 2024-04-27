package com.hlhs.hlhsrpc.springboot.starter.annotation;

import com.hlhs.hlhsrpc.springboot.starter.bootstrap.RpcConsumerBootstrap;
import com.hlhs.hlhsrpc.springboot.starter.bootstrap.RpcInitBootstrap;
import com.hlhs.hlhsrpc.springboot.starter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 是否需要启动server
     */
    boolean needServer() default true;
}
