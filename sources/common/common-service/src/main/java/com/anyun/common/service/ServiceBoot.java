package com.anyun.common.service;

import com.anyun.cloud.demo.common.registry.client.RegisterClient;
import com.anyun.cloud.demo.common.registry.client.RegistryModule;
import com.anyun.cloud.demo.common.registry.entity.NodeType;
import com.anyun.cloud.service.common.Service;
import com.anyun.common.lang.StringUtils;
import com.anyun.common.lang.bean.InjectorsBuilder;
import com.anyun.common.lang.msg.NatsClient;
import com.anyun.common.service.classloader.CloudServiceClassLoader;
import com.anyun.common.service.classloader.CloudServiceClassLoaderBuilder;
import com.anyun.common.service.common.*;
import com.anyun.common.service.git.GitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 12/06/2017
 */
public class ServiceBoot {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBoot.class);
    private PackageScanClassResolver resolver = new PackageScanClassResolver();
    private RegisterClient registerClient;
    private GitService gitService;

    private ServiceBoot() {
    }

    public static void boot(Class<?> clazz, String[] args) throws Exception {
        ServiceBoot boot = new ServiceBoot();
        boot.initGuiceService(args);
        boot.registerClient = InjectorsBuilder.getBuilder().getInstanceByType(RegisterClient.class);
        boot.gitService = InjectorsBuilder.getBuilder().getInstanceByType(GitService.class);
        CommServiceOptions options = InjectorsBuilder.getBuilder().getInstanceByType(CommServiceOptions.class);
        String jarPath = boot.getJarPath(options);
        boot.gitService.autoPullService();
        CloudServiceClassLoader classLoader = new CloudServiceClassLoaderBuilder()
                .withBuildBaseClass(clazz)
                .withDirectory(jarPath)
                .build();
        List<Class<? extends Service>> allCloudServiceClasses = classLoader.scan();
        ServiceCache serviceCache = InjectorsBuilder.getBuilder().getInstanceByType(ServiceCache.class);
        String deviceId = boot.registerClient.regist(Arrays.asList(NodeType.SERVICE_NODE));
        serviceCache.setDeviceId(deviceId);
        serviceCache.setCloudServiceClassLoader(classLoader);
        boot.registerClient.loopThread();
        InjectorsBuilder.getBuilder().getInstanceByType(NatsClient.class).start();
        for (Class<? extends Service> cloudServiceClass : allCloudServiceClasses) {
            InjectorsBuilder.getBuilder().getInstanceByType(ServiceDeployer.class).deploy(deviceId, cloudServiceClass);
        }
    }

    private void initGuiceService(String[] args) {
        CommServiceOptions options = new CommServiceOptions(args);
        InjectorsBuilder injectorsBuilder = InjectorsBuilder.getBuilder();
        injectorsBuilder.build(
                new RegistryModule(options),
                new ServiceCommonModule(options));
    }

    private String getJarPath(CommServiceOptions options) {
//        String jarPath = System.getenv("SERVICE_DEPLOY_DIR");
        String jarPath = System.getenv("SERVICE_DEPLOY_DIR");
        if (StringUtils.isNotEmpty(jarPath))
            return jarPath;
        if (options.getCommandLine().hasOption("service_dir"))
            jarPath = options.getCommandLine().getOptionValue("service_dir");
        if (StringUtils.isEmpty(jarPath))
            jarPath = "/opt/services";
        return jarPath;
    }
}
