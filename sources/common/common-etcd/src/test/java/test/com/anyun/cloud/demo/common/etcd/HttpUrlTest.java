package test.com.anyun.cloud.demo.common.etcd;

import okhttp3.HttpUrl;
import org.junit.Test;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class HttpUrlTest extends BaseEtcdTest {

    @Test
    public void test1() throws Exception{
        String path = "api/s1/s2/base";
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("http");
        builder.port(8080);
        builder.host("etcd.dev.hohhot.ga.gov");
        builder.addPathSegments(path);
        System.out.println(builder.build());
    }
}
