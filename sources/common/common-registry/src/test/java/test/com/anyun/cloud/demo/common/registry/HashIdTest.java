package test.com.anyun.cloud.demo.common.registry;

import com.anyun.cloud.demo.common.registry.utils.DeviceIdGenerator;
import org.junit.Test;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/5/18
 */
public class HashIdTest {

    @Test
    public void test1() throws Exception {
        DeviceIdGenerator generator = DeviceIdGenerator.getGenerator();
        for (int i = 0; i < 100; i++) {
            System.out.println(generator.generate(12));
        }
    }
}
