package com.anyun.common.lang.http;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/05/2017
 */
public class ServerConfig {
    private String host = "0.0.0.0";
    private int port = 80;
    private long idleTimeout = 30000;
    private boolean joinServerThread = true;
    private String uploadDir = "/tmp/upload";

    public ServerConfig() {
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

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
