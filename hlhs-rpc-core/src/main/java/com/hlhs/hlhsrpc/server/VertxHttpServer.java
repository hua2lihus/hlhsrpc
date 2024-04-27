package com.hlhs.hlhsrpc.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        //创建实例
        Vertx vertx = Vertx.vertx();

        //创建http服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();

        httpServer.requestHandler(new HttpServerHandler());


//        httpServer.requestHandler(request->{
//            //处理HTTP请求
//            System.out.println("请求信息："+request.method()+"url"+request.uri());
//            //发送HTTP响应
//            request.response().putHeader("Content-Type", "text/plain")
//                    .end("欢迎来到Vertx服务器","GBK");
//        });

        httpServer.listen(port,result->{
            if(result.succeeded()){
                System.out.println("HTTP服务器启动成功，监听端口："+port);
          }else{
                System.out.println("HTTP服务器启动失败："+result.cause()+"消息："+result.cause().getMessage());
            }
        });

    }
}
