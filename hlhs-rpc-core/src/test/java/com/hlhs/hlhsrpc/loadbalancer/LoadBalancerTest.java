package com.hlhs.hlhsrpc.loadbalancer;

import com.hlhs.hlhsrpc.model.ServicerMetaInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadBalancerTest {
    final LoadBalancer loadBalancer = new RandomLoadBalancer();

    @Test
    public void select() {
        // 请求参数
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("methodName", "apple");
        // 服务列表
        ServicerMetaInfo serviceMetaInfo1 = new ServicerMetaInfo();
        serviceMetaInfo1.setServiceName("myService");
        serviceMetaInfo1.setServiceVersion("1.0");
        serviceMetaInfo1.setServiceHost("localhost");
        serviceMetaInfo1.setServicePort(1234);
        ServicerMetaInfo serviceMetaInfo2 = new ServicerMetaInfo();
        serviceMetaInfo2.setServiceName("myService");
        serviceMetaInfo2.setServiceVersion("1.0");
        serviceMetaInfo2.setServiceHost("yupi.icu");
        serviceMetaInfo2.setServicePort(80);
        List<ServicerMetaInfo> serviceMetaInfoList = Arrays.asList(serviceMetaInfo1, serviceMetaInfo2);
        // 连续调用 3 次
//        ServicerMetaInfo serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
//        System.out.println(serviceMetaInfo);
//        Assert.assertNotNull(serviceMetaInfo);
//        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
//        System.out.println(serviceMetaInfo);
//        Assert.assertNotNull(serviceMetaInfo);
//        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
//        System.out.println(serviceMetaInfo);
//        Assert.assertNotNull(serviceMetaInfo);

        ServicerMetaInfo serviceMetaInfo = null;
        for(int i=0;i<100;i++){
            serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
            System.out.println("第"+(i+1)+"次调用"+serviceMetaInfo);
            Assert.assertNotNull(serviceMetaInfo);
        }
    }
}
