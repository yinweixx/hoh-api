package test.com.anyun.cloud.demo.common.etcd;

import com.anyun.cloud.demo.common.etcd.client.OkHttpRestfullyApiClient;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionResponse;
import com.anyun.cloud.demo.common.etcd.EtcdErrorResponseException;
import com.anyun.cloud.demo.common.etcd.GsonUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class EtcdApiJsonFormatTest extends BaseEtcdTest {
    private OkHttpRestfullyApiClient client;
    private String response1 = null;
    private String response2 = null;
    private String response3 = null;

    @Before
    public void before() {
        response1 = "{\"action\":\"get\",\"node\":{\"key\":\"/config/zookeeper/connection-string\",\"value\":\"192.168.103.4:2181\",\"modifiedIndex\":16,\"createdIndex\":16}}";
        response2 = "{\"action\":\"get\",\"node\":{\"key\":\"/config\",\"dir\":true,\"nodes\":[{\"key\":\"/config/api\",\"dir\":true,\"modifiedIndex\":9,\"createdIndex\":9},{\"key\":\"/config/zookeeper\",\"dir\":true,\"modifiedIndex\":14,\"createdIndex\":14}],\"modifiedIndex\":9,\"createdIndex\":9}}";
        response3 = "{\"errorCode\":100,\"message\":\"Key not found\",\"cause\":\"/config/aa\",\"index\":16}";
        client = new OkHttpRestfullyApiClient(null);
    }

    @Test
    public void test1() throws Exception {
        EtcdActionResponse response = GsonUtil.getUtil().getReponseEntity(response1);
        System.out.println("Response: " + response);
        assertNotNull(response);

    }

    @Test
    public void test2() throws Exception {
        EtcdActionResponse response = GsonUtil.getUtil().getReponseEntity(response2);
        System.out.println("Response: " + response);
        assertNotNull(response);

    }

    @Test
    public void test3() throws Exception {
        EtcdActionResponse response = null;
        try {
            response = GsonUtil.getUtil().getReponseEntity(response3);

        } catch (EtcdErrorResponseException ex) {
            System.out.println("Error message: " + ex.getMessage());
            assertNotNull(ex.getMessage());
        }
        assertNull(response);
    }
}
