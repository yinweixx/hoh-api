package test.com.anyun.cloud.demo.message.client;

import io.nats.client.Connection;
import io.nats.client.ConnectionFactory;

import java.util.UUID;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class QueueSubscribe {
    private static final String CONNSTR = "nats://172.20.2.1:4222";
    private static final String CHANNEL_TEST = "foo";

    public static void main(String[] args) throws Exception{
        String id = UUID.randomUUID().toString();
        ConnectionFactory factory = new ConnectionFactory(CONNSTR);
        Connection nc = factory.createConnection();

        nc.subscribe(CHANNEL_TEST,"QUEUE-" + CHANNEL_TEST,message -> {
            System.out.println("ID [" + id + "] MSG: " + message);
            try {
//                Thread.sleep(new Random().nextInt(10* 1000));
                byte[] m = new String("[" + id + "] ReplyTo-" + System.currentTimeMillis()).getBytes();
//                nc.publish(message.getReplyTo(),m);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        });
    }
}
