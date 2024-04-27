package com.hlhs.hlhsrpc.server;

import com.hlhs.hlhsrpc.RpcApplication;
import com.hlhs.hlhsrpc.model.RpcRequest;
import com.hlhs.hlhsrpc.model.RpcResponse;
import com.hlhs.hlhsrpc.registry.LocalRegister;
import com.hlhs.hlhsrpc.serializer.JdkSerializer;
import com.hlhs.hlhsrpc.serializer.Serializer;
import com.hlhs.hlhsrpc.serializer.SerializerFactory;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;



import java.io.IOException;
import java.lang.reflect.Method;

public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {
        //指定序列化器
        final Serializer serializer= SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        //记录日志
        System.out.println("请求信息方法："+request.method()+" 路径："+request.uri());

        //异步处理Http请求
        request.bodyHandler(body->{
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest=null;
            try{
                rpcRequest=serializer.deserialize(bytes,RpcRequest.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            //构造响应结果对象
            RpcResponse rpcResponse=new RpcResponse();
            //如果请求为null，直接返回
            if(rpcRequest==null){
                rpcResponse.setMessage("请求参数为空");
             doResponse(request,rpcResponse,serializer);
                return ;
            }



            try {
                Class<?> implClass = LocalRegister.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            doResponse(request,rpcResponse,serializer);
        });
    }

    /**
     * 响应
     * @param request
     * @param rpcResponse
     * @param serializer
     */
    private void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response().putHeader("content-type", "application/json");

        try {
            byte[] serialize = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(serialize));
        } catch (IOException e) {
           e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
