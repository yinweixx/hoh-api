package com.anyun.common.lang.http;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 23/05/2017
 */
public class ServerStatus {
    private boolean isRunning = false;
    private Date startupTime = null;
    private long startMillisecond = 0;
    private long accessCount = 0;
    private List<AccessStatus> accessStatuses = new ArrayList<>();

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Date getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(Date startupTime) {
        this.startupTime = startupTime;
    }

    public long getStartMillisecond() {
        return startMillisecond;
    }

    public void setStartMillisecond(long startMillisecond) {
        this.startMillisecond = startMillisecond;
    }

    public long getAccessCount() {
        return accessCount;
    }

    public void setAccessCount(long accessCount) {
        this.accessCount = accessCount;
    }

    public List<AccessStatus> getAccessStatuses() {
        return accessStatuses;
    }

    public void setAccessStatuses(List<AccessStatus> accessStatuses) {
        this.accessStatuses = accessStatuses;
    }

    @Override
    public String toString() {
        return "ServerStatus{" +
                "isRunning=" + isRunning +
                ", startupTime=" + startupTime +
                ", startMillisecond=" + startMillisecond +
                '}';
    }

    public static class AccessStatus {
        private String path = "";
        private String method = "";
        private long count = 0;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public long getCount() {
            return count;
        }

        public void setCount(long count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "AccessStatus{" +
                    "path='" + path + '\'' +
                    ", method='" + method + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
