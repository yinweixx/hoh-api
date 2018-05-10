package com.anyun.common.service.common;

import com.anyun.cloud.service.common.Service;
import com.anyun.common.service.classloader.CloudServiceClassLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ServiceCache {
    private Map<String, Service> services;
    private String deviceId;
    private CloudServiceClassLoader cloudServiceClassLoader;

    public ServiceCache() {
        services = new HashMap<>();
    }

    public <T extends Service> void addService(String resourceId, T service) {
        services.put(resourceId, service);
    }

    public Service getService(String resourceId) {
        return services.get(resourceId);
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public CloudServiceClassLoader getCloudServiceClassLoader() {
        return cloudServiceClassLoader;
    }

    public void setCloudServiceClassLoader(CloudServiceClassLoader cloudServiceClassLoader) {
        this.cloudServiceClassLoader = cloudServiceClassLoader;
    }
}
