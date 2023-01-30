package com.remoc.sync.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String extranet;//用于外网访问

    private String endpoint;//minio地址+端口号,用于内网部署

    private String accessKey;//minio用户名

    private String secretKey;//minio密码

    public String getExtranet() {
        return extranet;
    }

    public void setExtranet(String extranet) {
        this.extranet = extranet;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

