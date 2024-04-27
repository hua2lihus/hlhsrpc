package com.hlhs.hlhsrpc.loadbalancer;

import com.hlhs.hlhsrpc.model.ServicerMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 一致性hash负载均衡器
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {
    /**
     * 一致性hash环，存放虚拟节点
     */
    private final TreeMap<Integer, ServicerMetaInfo> virtualNodes = new TreeMap<>();


    private static final int VIRTUAL_NODES_NUM = 100;

    @Override
    public ServicerMetaInfo select(Map<String, Object> requestParams, List<ServicerMetaInfo> servicerMetaInfos) {
        if (servicerMetaInfos.isEmpty()) {
            return null;
        }
        //构建虚拟节点环
        for (ServicerMetaInfo item : servicerMetaInfos) {
            for (int i = 0; i < VIRTUAL_NODES_NUM; i++) {
                int hash = getHash(item.getServiceAddress() + "#" + i);
                virtualNodes.put(hash, item);
            }
        }
        //获取请求德hash值
        int hash = getHash(requestParams);
        //获取最近的节点
        Map.Entry<Integer, ServicerMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if(entry==null){
            entry=virtualNodes.firstEntry();
        }

        return entry.getValue();
    }

    private int getHash(Object s) {
        return s.hashCode();
    }
    //todo requestParams可以是消费者的ip地址， map存的是服务的地址。 根据ip生成hash值， 然后根据hash值获取最近的服务地址
}
