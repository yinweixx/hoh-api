package test.com.anyun.common.service;

import com.anyun.cloud.service.common.CloudService;
import com.anyun.common.service.common.PackageScanClassResolver;
import org.junit.Test;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class PackageScanClassResolverTest extends BaseTest {

    @Test
    public void test1() throws Exception {
        PackageScanClassResolver resolver = new PackageScanClassResolver()
                .withBaseClass(PackageScanClassResolverTest.class);
        assertEquals(1, resolver.scanCloudServiceClassByPackageName("test.com.anyun.common.service").size());
    }

    @CloudService(servicePath = "/api/api1")
    public static class TestCloudService {

    }
}
