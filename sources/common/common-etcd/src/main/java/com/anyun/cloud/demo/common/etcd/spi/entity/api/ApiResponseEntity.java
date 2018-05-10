package com.anyun.cloud.demo.common.etcd.spi.entity.api;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 2017/6/5
 */
public class ApiResponseEntity {
    private int code;
    private String contentType;
    private ApiTypeEntity typeEntity;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ApiTypeEntity getTypeEntity() {
        return typeEntity;
    }

    public void setTypeEntity(ApiTypeEntity typeEntity) {
        this.typeEntity = typeEntity;
    }

    @Override
    public String toString() {
        return "ApiResponseEntity{" +
                "code=" + code +
                ", contentType='" + contentType + '\'' +
                ", typeEntity=" + typeEntity +
                '}';
    }
}