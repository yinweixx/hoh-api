package com.anyun.cloud.demo.api.management.http;

import com.anyun.cloud.demo.common.etcd.EtcdErrorResponseException;
import com.anyun.cloud.demo.common.etcd.response.EtcdActionResponse;
import com.anyun.cloud.demo.common.etcd.spi.entity.AbstractEtcdEntity;
import com.anyun.common.lang.http.ServerConfig;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 25/05/2017
 */
public class ApiManagementServerConfigEntity extends AbstractEtcdEntity<ApiManagementServerConfigEntity> {
    public static String ETCD_KEY_CONFIG_ZK = "/keys/config/api-portal";
    public static String ETCD_KEY_MGR_HOST = "host";
    public static String ETCD_KEY_MGR_PORT = "port";
    public static String ETCD_KEY_MGR_IDLE_TIMEOUT = "idle-timeout";
    public static String ETCD_KEY_MGR_SERVLET_MAPPING = "servlet-mapping-path";
    public static String ETCD_KEY_MGR_HTTP_SERVER_THREAD_JOIN = "thread-join";
    public static String ETCD_KEY_MGR_HTTP_DIR_UPLOAD = "dir-upload";

    private String host = "0.0.0.0";
    private int port = 80;
    private long idleTimeout = 30000;
    private boolean joinServerThread = true;
    private String uploadDir;

    public ApiManagementServerConfigEntity buildFromEtcdActionResponse(EtcdActionResponse response)
            throws EtcdErrorResponseException {
        ApiManagementServerConfigEntity config = new ApiManagementServerConfigEntity();
        try {
            config.host = getStringValue(response, ETCD_KEY_MGR_HOST);
            config.port = getIntValue(response, ETCD_KEY_MGR_PORT);
            config.idleTimeout = getLongValue(response, ETCD_KEY_MGR_IDLE_TIMEOUT);
            config.joinServerThread = getBooleanValue(response, ETCD_KEY_MGR_HTTP_SERVER_THREAD_JOIN);
            config.uploadDir = getStringValue(response, ETCD_KEY_MGR_HTTP_DIR_UPLOAD);
            return config;
        } catch (Exception ex) {
            throw new EtcdErrorResponseException(ex);
        }
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public boolean isJoinServerThread() {
        return joinServerThread;
    }

    public void setJoinServerThread(boolean joinServerThread) {
        this.joinServerThread = joinServerThread;
    }

    public ServerConfig asConfig() {
        ServerConfig config = new ServerConfig();
        config.setHost(this.getHost());
        config.setPort(this.getPort());
        config.setIdleTimeout(this.getIdleTimeout());
        config.setJoinServerThread(this.isJoinServerThread());
        config.setUploadDir(this.getUploadDir());
        return config;
    }

    @Override
    public String toString() {
        return "ApiManagementServerConfigEntity {" + "\n" +
                "    host='" + host + "\'\n" +
                "    port=" + port + "\n" +
                "    idleTimeout=" + idleTimeout + "\n" +
                "    joinServerThread=" + joinServerThread + "\n" +
                "    uploadDir='" + uploadDir + "\'\n" +
                '}';
    }
}
