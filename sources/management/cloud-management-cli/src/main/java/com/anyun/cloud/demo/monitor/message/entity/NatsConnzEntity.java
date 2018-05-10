package com.anyun.cloud.demo.monitor.message.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/6
 */
public class NatsConnzEntity {
    @SerializedName("now")
    private String nowTime;
    @SerializedName("num_connections")
    private int connectionNumbers;
    @SerializedName("total")
    private int total;
    @SerializedName("offset")
    private int offset;
    @SerializedName("limit")
    private int limit;
    @SerializedName("connections")
    private List<NatsConnEntity> conns = new ArrayList<>();

    public List<NatsConnEntity> getConns() {
        return conns;
    }

    public void setConns(List<NatsConnEntity> conns) {
        this.conns = conns;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    public int getConnectionNumbers() {
        return connectionNumbers;
    }

    public void setConnectionNumbers(int connectionNumbers) {
        this.connectionNumbers = connectionNumbers;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "NatsConnzEntity {" + "\n" +
                "    nowTime='" + nowTime + "\'\n" +
                "    connectionNumbers=" + connectionNumbers + "\n" +
                "    total=" + total + "\n" +
                "    offset=" + offset + "\n" +
                "    limit=" + limit + "\n" +
                "    conns=" + conns + "\n" +
                '}';
    }

    public class NatsConnEntity {
        @SerializedName("cid")
        private long cid;
        @SerializedName("ip")
        private String ip;
        @SerializedName("port")
        private int port;
        @SerializedName("start")
        private String start;
        @SerializedName("last_activity")
        private String lastActivity;
        @SerializedName("uptime")
        private String uptime;
        @SerializedName("idle")
        private String idle;
        @SerializedName("pending_bytes")
        private long pendingBytes;
        @SerializedName("in_msgs")
        private long inMessages;
        @SerializedName("out_msgs")
        private long outMessage;
        @SerializedName("in_bytes")
        private long inBytes;
        @SerializedName("out_bytes")
        private long outBytes;
        @SerializedName("subscriptions")
        private int subscriptions;
        @SerializedName("lang")
        private String lang;
        @SerializedName("version")
        private String version;

        public long getCid() {
            return cid;
        }

        public void setCid(long cid) {
            this.cid = cid;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getLastActivity() {
            return lastActivity;
        }

        public void setLastActivity(String lastActivity) {
            this.lastActivity = lastActivity;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public String getIdle() {
            return idle;
        }

        public void setIdle(String idle) {
            this.idle = idle;
        }

        public long getPendingBytes() {
            return pendingBytes;
        }

        public void setPendingBytes(long pendingBytes) {
            this.pendingBytes = pendingBytes;
        }

        public long getInMessages() {
            return inMessages;
        }

        public void setInMessages(long inMessages) {
            this.inMessages = inMessages;
        }

        public long getOutMessage() {
            return outMessage;
        }

        public void setOutMessage(long outMessage) {
            this.outMessage = outMessage;
        }

        public long getInBytes() {
            return inBytes;
        }

        public void setInBytes(long inBytes) {
            this.inBytes = inBytes;
        }

        public long getOutBytes() {
            return outBytes;
        }

        public void setOutBytes(long outBytes) {
            this.outBytes = outBytes;
        }

        public int getSubscriptions() {
            return subscriptions;
        }

        public void setSubscriptions(int subscriptions) {
            this.subscriptions = subscriptions;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return "NatsConnEntity {" + "\n" +
                    "    cid=" + cid + "\n" +
                    "    ip='" + ip + "\'\n" +
                    "    port=" + port + "\n" +
                    "    start='" + start + "\'\n" +
                    "    lastActivity='" + lastActivity + "\'\n" +
                    "    uptime='" + uptime + "\'\n" +
                    "    idle='" + idle + "\'\n" +
                    "    pendingBytes=" + pendingBytes + "\n" +
                    "    inMessages=" + inMessages + "\n" +
                    "    outMessage=" + outMessage + "\n" +
                    "    inBytes=" + inBytes + "\n" +
                    "    outBytes=" + outBytes + "\n" +
                    "    subscriptions=" + subscriptions + "\n" +
                    "    lang='" + lang + "\'\n" +
                    "    version='" + version + "\'\n" +
                    '}';
        }
    }
}
