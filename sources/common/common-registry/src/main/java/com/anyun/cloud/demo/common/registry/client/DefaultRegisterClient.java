package com.anyun.cloud.demo.common.registry.client;

import com.anyun.cloud.demo.common.registry.entity.NodeEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.cloud.demo.common.registry.service.NodeRegistService;
import com.anyun.cloud.demo.common.registry.utils.DeviceIdGenerator;
import com.anyun.common.lang.options.ApplicationOptions;
import com.anyun.common.lang.zookeeper.ZookeeperClient;
import com.google.inject.Singleton;
import org.apache.commons.cli.CommandLine;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 07/06/2017
 */
@Singleton
public class DefaultRegisterClient implements RegisterClient {
    private static final String OPT_TYPES = "types";
    private ApplicationOptions options;
    private NodeRegistService service;
    private ZookeeperClient zkClient;
    private Thread loopThread;

    @Inject
    public DefaultRegisterClient(ApplicationOptions options, NodeRegistService service, ZookeeperClient zkClient) {
        this.options = options;
        this.service = service;
        this.zkClient = zkClient;
    }

    @Override
    public String regist() throws Exception {
        CommandLine cl = options.getCommandLine();
        if (!cl.hasOption(OPT_TYPES))
            throw new Exception("Node type argument is not set");
        String[] types = cl.getOptionValue(OPT_TYPES).split(",");
        if (types == null || types.length == 0)
            throw new Exception("Node type argument is not set");
        List<NodeType> nodeTypes = new ArrayList<>();
        for (String type : types) {
            nodeTypes.add(NodeType.valueOf(type));
        }
        if (nodeTypes.isEmpty())
            throw new Exception("Node type argument is not set");
        return regist(nodeTypes);
    }

    @Override
    public String regist(List<NodeType> nodeTypes) throws Exception {
        NodeEntity entity = new NodeEntity();
        String deviceId = DeviceIdGenerator.getGenerator().generate();
        long currentTime = System.currentTimeMillis();
        entity.setUid(deviceId);
        entity.setNodeType(nodeTypes);
        entity.setTimestamp(currentTime);
        entity.setUpstartTimestamp(currentTime);
        entity.setNetworks(DeviceIdGenerator.getDeviceNetworks());
        zkClient.start();
        service.registNode(entity);
        return deviceId;
    }

    @Override
    public void loopThread() throws Exception {
        if (loopThread != null)
            return;
        loopThread = new Thread(new Runnable() {
            private CountDownLatch latch = new CountDownLatch(1);

            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        loopThread.start();
    }
}
