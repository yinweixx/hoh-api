package test.com.anyun.cloud.demo.common.etcd;

import org.junit.Assert;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/19
 */
public class BaseEtcdTest extends Assert {
    public static final String DOMAIN_ETCD = "etcd.dev.hohhot.ga.gov";
    public static final String BASE_URL = "http://" + DOMAIN_ETCD + ":2379/v2";
}
