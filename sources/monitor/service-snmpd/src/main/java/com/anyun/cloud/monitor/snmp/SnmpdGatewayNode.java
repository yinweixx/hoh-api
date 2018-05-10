package com.anyun.cloud.monitor.snmp;

import org.soulwing.snmp.*;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 15/06/2017
 */
public class SnmpdGatewayNode {
    public static void main(String[] args) throws Throwable {
        SimpleSnmpV2cTarget target = new SimpleSnmpV2cTarget();
        target.setAddress("192.168.103.2");
        target.setCommunity("1234qwer");
        SnmpContext context = SnmpFactory.getInstance().newContext(target);
        while(true) {
            VarbindCollection varbinds = context.get(".1.3.6.1.4.1.8072.9999.1.4.1.2.9.115.104.101.108.108.116.101.115.116.2").get();
            for (Varbind varbind : varbinds) {
                System.out.format("%s=%s\n", varbind.getName(), new String((byte[]) varbind.toObject()));
            }
            Thread.sleep(1000L);
        }
    }
}
