package com.anyun.cloud.demo.common.registry.utils;

import com.anyun.cloud.demo.common.registry.entity.NodeNetworkEntity;
import com.anyun.common.lang.NetworkUtils;
import com.anyun.common.lang.StringUtils;
import org.hashids.Hashids;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class DeviceIdGenerator {
    private static DeviceIdGenerator generator = null;

    private DeviceIdGenerator() {

    }

    public static DeviceIdGenerator getGenerator() {
        synchronized (DeviceIdGenerator.class) {
            if (generator == null)
                generator = new DeviceIdGenerator();
        }
        return generator;
    }

    public String generate(int length) {
        if (length < 4)
            length = 4;
        String salt = getUuidSalt();
        Hashids hashids = new Hashids(salt, length);
        String hash = hashids.encode(System.nanoTime());
        return hash;
    }

    public static List<NodeNetworkEntity> getDeviceNetworks() throws Exception {
        List<NodeNetworkEntity> nodeNetworkEntities = new ArrayList<>();
        Map<String, List<InterfaceAddress>> networks = NetworkUtils.getInterfacesAddersses();
        for (Map.Entry<String, List<InterfaceAddress>> entry : networks.entrySet()) {
            NodeNetworkEntity networkInfoEntity = new NodeNetworkEntity();
            String name = entry.getKey();
            List<InterfaceAddress> addresses = entry.getValue();
            networkInfoEntity.setEtherName(name);
            List<String> ipList = new ArrayList<>();
            for (InterfaceAddress address : addresses) {
                InetAddress inetAddress = address.getAddress();
                if (!(inetAddress instanceof Inet4Address))
                    continue;
                if (address.getBroadcast() == null)
                    continue;
                String ipAddr = inetAddress.getHostAddress();
                if(StringUtils.isEmpty(ipAddr))
                    continue;
                ipList.add(ipAddr);
            }
            if(ipList.isEmpty())
                continue;
            networkInfoEntity.setIp(ipList);
            nodeNetworkEntities.add(networkInfoEntity);
        }
        return nodeNetworkEntities;
    }

    public String generate() {
        return generate(8);
    }

    private String getUuidSalt() {
        return UUID.randomUUID().toString();
    }
}
