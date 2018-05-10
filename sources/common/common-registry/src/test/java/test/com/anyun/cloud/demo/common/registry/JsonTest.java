package test.com.anyun.cloud.demo.common.registry;

import com.anyun.cloud.demo.common.registry.entity.NodeEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeNetworkEntity;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.cloud.demo.common.registry.utils.DeviceIdGenerator;
import com.anyun.common.lang.NetworkUtils;
import org.junit.Test;

import java.net.InterfaceAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class JsonTest extends BaseRegistryTest {
    @Test
    public void test1() throws Exception {
        NodeEntity entity = new NodeEntity();
        entity.setNodeType(Arrays.asList(NodeType.MGR_NODE));
        entity.setTimestamp(System.currentTimeMillis());
        entity.setUid(DeviceIdGenerator.getGenerator().generate());
        entity.setUpstartTimestamp(System.currentTimeMillis());
        Map<String, List<InterfaceAddress>> networks = NetworkUtils.getInterfacesAddersses();
        List<NodeNetworkEntity> nodeNetworkEntities = new ArrayList<>();
        for (Map.Entry<String, List<InterfaceAddress>> entry : networks.entrySet()) {
            NodeNetworkEntity networkInfoEntity = new NodeNetworkEntity();
            String name = entry.getKey();
            List<InterfaceAddress> addresses = entry.getValue();
            networkInfoEntity.setEtherName(name);
            List<String> ipList = new ArrayList<>();
            for (InterfaceAddress address : addresses) {
                ipList.add(address.getAddress().getHostAddress());
            }
            networkInfoEntity.setIp(ipList);
            nodeNetworkEntities.add(networkInfoEntity);
        }
        entity.setNetworks(nodeNetworkEntities);
        System.out.println(com.anyun.common.lang.json.GsonUtil.getUtil().toJson(entity));
    }
}
