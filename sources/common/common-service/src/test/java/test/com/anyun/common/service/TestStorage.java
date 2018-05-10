package test.com.anyun.common.service;

import io.minio.MinioClient;

/**
 * @auth TwitchGG <twitchgg@yahoo.com>
 * @since 1.0.0 on 04/07/2017
 */
public class TestStorage {
    public static void main(String[] args) throws Exception{
        String addr = "http://192.168.103.14";
        int port = 9000;
        String accessKey = "anyuncloud";
        String secretKey = "anyuncloud123";
        MinioClient minioClient = new MinioClient(addr, port, accessKey, secretKey);

    }
}
