package com.hlhs.hlhsrpc.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invoke {}",method.getName());
        return getDefaultObject(methodReturnType);
    }

    private Object getDefaultObject(Class<?> methodReturnType) {
        //判断是否为基本类型
        if(methodReturnType.isPrimitive()){
            if(methodReturnType== int.class){
                return 0;
            }else if(methodReturnType== long.class){
                return 0L;
            }else if(methodReturnType== boolean.class){
                return false;
            }else if(methodReturnType== float.class){
                return 0.0F;
            }else if(methodReturnType== double.class){
                return 0.0D;
            }else if(methodReturnType== byte.class){
                return (byte) 0;
            } else if(methodReturnType == short.class){
                return (short)0;
            }
        }
        return null;
    }
}
