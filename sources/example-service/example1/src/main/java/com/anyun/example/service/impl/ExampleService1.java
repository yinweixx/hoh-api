package com.anyun.example.service.impl;

import com.anyun.cloud.service.common.CloudService;
import com.anyun.cloud.service.common.Service;
import com.anyun.cloud.service.common.exchange.Exchange;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 06/06/2017
 */
@CloudService(servicePath = "/app1/api/v1_0_0/api1")
public class ExampleService1 implements Service<ExampleService1.TestResultEntity> {

    @Override
    public TestResultEntity onExchange(Exchange exchange) {
        Map<String, List<String>> headers = exchange.getIn().getHeaders();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.println("Head Name: " + entry.getKey() + "    Value: " + entry.getValue());
        }
        Map<String, List<String>> querys = exchange.getIn().getQuery();
        for (Map.Entry<String, List<String>> entry : querys.entrySet()) {
            System.out.println("Query Name: " + entry.getKey() + "    Value: " + entry.getValue());
        }

        String deviceId = exchange.getSessionContext().getServiceContext().getDeviceId();
        TestResultEntity entity = new TestResultEntity();
        entity.setDeviceId(deviceId);
        entity.setDate(new Date().toString());
        return entity;
    }

    public static class TestResultEntity {
        private String deviceId;
        private String date;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
