package test.com.anyun.common.service;

import com.anyun.common.service.ServiceBoot;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 30/06/2017
 */
public class TestServiceLauncher {
    public static void main(String[] args) throws Exception {
        //Develop testing env
        args = new String[]{
                "-etcdaddr", "192.168.103.7"
        };
        System.setProperty("SERVICE_GIT_URL", "http://git.dev.hohhot.ga.gov/pgis/service-example1.git");
        System.setProperty("SERVICE_DEPLOY_DIR", "/Users/twitchgg/Develop/temp/hohot-cloud-demo/git");
        // End env
        ServiceBoot.boot(TestServiceLauncher.class, args);
    }
}
