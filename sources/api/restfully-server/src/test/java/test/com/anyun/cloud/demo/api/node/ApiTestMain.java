package test.com.anyun.cloud.demo.api.node;

import com.anyun.cloud.demo.common.etcd.client.ClientConfig;
import com.anyun.cloud.demo.common.etcd.client.OkHttpRestfullyApiClient;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ApiTestMain {
    public static void main(String[] args) throws Exception {
        String PATH = "/app1/api/v1_0_0/api1";
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setBasePathSegment("api");
        clientConfig.setHost("192.168.101.70");
        clientConfig.setPort(8080);
        OkHttpRestfullyApiClient client = new OkHttpRestfullyApiClient(clientConfig);
        while (true) {
            System.out.println(client.get(PATH, null));
            Thread.sleep(500L);
        }
    }
}
