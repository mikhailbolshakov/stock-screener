package com.mikhailb.stockscreener.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {
    private final ConnectionProperties connection = new ConnectionProperties();
}
