package test.com.anyun.cloud.demo.message.client;

import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;
import io.nats.client.Message;

import java.io.IOException;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/5
 */
public class SenderTest {
    private static final String CONNSTR = "nats://172.20.2.1:4222";
    private static final String CHANNEL_TEST = "foo";

    public SenderTest() throws IOException {

    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory(CONNSTR);
        Connection nc = factory.createConnection();

//        new StatisticsRunnable(nc).withLoopTime(5).start();
        new Thread(() -> {
            long count = 0;
            try {
                while (true) {
                    count++;
                    byte[] m = new String("Hello-" + count + "    " + System.currentTimeMillis()).getBytes();
                    Message message = nc.request(CHANNEL_TEST, m,5000);
                    System.out.println(new String(message.getData()));
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
